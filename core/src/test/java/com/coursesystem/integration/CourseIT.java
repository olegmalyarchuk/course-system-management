package com.coursesystem.integration;

import com.coursesystem.dto.CourseDTO;
import com.coursesystem.mapper.CourseMapper;
import com.coursesystem.model.Course;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.util.JsonUtil;
import com.coursesystem.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
class CourseIT {

    private static final String DEFAULT_NAME = "DEFAULT_NAME";
    private static final String UPDATED_NAME = "UPDATED_NAME";

    private static final Short DEFAULT_NUMBER_OF_LESSONS = 10;
    private static final Short UPDATED_NUMBER_OF_LESSONS = 15;

    private static final String ENTITY_API_URL = "/api/v1/courses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseMockMvc;

    @Autowired
    private TestUtil testUtil;

    private Course course;

    public Course createEntity(EntityManager em) {
        Course course = Course.builder()
                .name(DEFAULT_NAME)
                .numberOfLessons(DEFAULT_NUMBER_OF_LESSONS)
                .instructors(Collections.singleton(testUtil.createTestInstructor()))
                .build();
        return course;
    }

    @BeforeEach
    public void initTest() {
        course = createEntity(em);
    }

    @Test
    @Transactional
    void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        CourseDTO courseDTO = courseMapper.toDto(course);
        restCourseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(courseDTO)))
                .andExpect(status().isCreated());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate + 1);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCourse.getNumberOfLessons()).isEqualTo(DEFAULT_NUMBER_OF_LESSONS);
    }

    @Test
    @Transactional
    void createCourseWithExistingId() throws Exception {
        course.setId(1L);
        CourseDTO courseDTO = courseMapper.toDto(course);

        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        restCourseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(courseDTO)))
                .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        course.setName(null);

        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(courseDTO)))
                .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourses() throws Exception {
        courseRepository.saveAndFlush(course);

        restCourseMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(jsonPath("$.[*].numberOfLessons").value(hasItem(DEFAULT_NUMBER_OF_LESSONS.intValue())));
    }

    @Test
    @Transactional
    void getCourse() throws Exception {
        courseRepository.saveAndFlush(course);

        restCourseMockMvc
                .perform(get(ENTITY_API_URL_ID, course.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(course.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
                .andExpect(jsonPath("$.numberOfLessons").value(DEFAULT_NUMBER_OF_LESSONS.intValue()))
                .andExpect(jsonPath("$.instructorIds").value(hasSize(1)));
    }

    @Test
    @Transactional
    void getNonExistingCourse() throws Exception {
        restCourseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourse() throws Exception {
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        Course updatedCourse = courseRepository.findById(course.getId()).get();

        em.detach(updatedCourse);
        updatedCourse.setName(UPDATED_NAME);
        updatedCourse.setNumberOfLessons(UPDATED_NUMBER_OF_LESSONS);
        CourseDTO courseDTO = courseMapper.toDto(updatedCourse);

        restCourseMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, courseDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(courseDTO))
                )
                .andExpect(status().isOk());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCourse.getNumberOfLessons()).isEqualTo(UPDATED_NUMBER_OF_LESSONS);
    }

    @Test
    @Transactional
    void putNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();
        course.setId(count.incrementAndGet());

        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, courseDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(courseDTO))
                )
                .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();
        course.setId(count.incrementAndGet());

        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, count.incrementAndGet())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(courseDTO))
                )
                .andExpect(status().isBadRequest());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();
        course.setId(count.incrementAndGet());

        CourseDTO courseDTO = courseMapper.toDto(course);

        restCourseMockMvc
                .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(courseDTO)))
                .andExpect(status().isMethodNotAllowed());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourse() throws Exception {
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        restCourseMockMvc
                .perform(delete(ENTITY_API_URL_ID, course.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
