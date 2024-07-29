package com.coursesystem.integration;

import com.coursesystem.dto.CourseReviewDTO;
import com.coursesystem.mapper.CourseReviewMapper;
import com.coursesystem.model.Course;
import com.coursesystem.model.CourseReview;
import com.coursesystem.repository.CourseReviewRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
class CourseReviewIT {

    private static final String DEFAULT_REVIEW = "DEFAULT_REVIEW";
    private static final String UPDATED_REVIEW = "UPDATED_REVIEW";

    private static final String ENTITY_API_URL = "/api/v1/course-review";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourseReviewRepository courseReviewRepository;

    @Autowired
    private CourseReviewMapper courseReviewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseReviewMockMvc;

    @Autowired
    private TestUtil testUtil;

    private CourseReview courseReview;

    public CourseReview createEntity(EntityManager em) {
        final Course course = testUtil.createCourse();
        CourseReview courseReview = CourseReview.builder()
                .review(DEFAULT_REVIEW)
                .course(course)
                .student(testUtil.createTestStudentWithCourse(Collections.singleton(course)))
                .build();
        return courseReview;
    }

    @BeforeEach
    public void initTest() {
        courseReview = createEntity(em);
    }

    @Test
    @Transactional
    void createCourseReview() throws Exception {
        int databaseSizeBeforeCreate = courseReviewRepository.findAll().size();

        CourseReviewDTO courseReviewDTO = courseReviewMapper.toDto(courseReview);
        restCourseReviewMockMvc
                .perform(post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(courseReviewDTO)))
                .andExpect(status().isCreated());

        List<CourseReview> courseReviewList = courseReviewRepository.findAll();
        assertThat(courseReviewList).hasSize(databaseSizeBeforeCreate + 1);
        CourseReview testCourseReview = courseReviewList.get(courseReviewList.size() - 1);
        assertThat(testCourseReview.getReview()).isEqualTo(DEFAULT_REVIEW);
    }

    @Test
    @Transactional
    void createCourseReviewWithExistingId() throws Exception {
        courseReview.setId(1L);
        CourseReviewDTO courseReviewDTO = courseReviewMapper.toDto(courseReview);

        int databaseSizeBeforeCreate = courseReviewRepository.findAll().size();

        restCourseReviewMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(courseReviewDTO)))
                .andExpect(status().isBadRequest());

        List<CourseReview> courseReviewList = courseReviewRepository.findAll();
        assertThat(courseReviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReviewIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseReviewRepository.findAll().size();

        courseReview.setReview(null);

        CourseReviewDTO courseReviewDTO = courseReviewMapper.toDto(courseReview);

        restCourseReviewMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(courseReviewDTO)))
                .andExpect(status().isBadRequest());

        List<CourseReview> courseReviewList = courseReviewRepository.findAll();
        assertThat(courseReviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCourseReviews() throws Exception {
        courseReviewRepository.saveAndFlush(courseReview);

        restCourseReviewMockMvc
                .perform(get("/api/v1/course-reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courseReview.getId().intValue())))
                .andExpect(jsonPath("$.[*].review").value(hasItem(DEFAULT_REVIEW)));
    }

    @Test
    @Transactional
    void getCourseReview() throws Exception {
        courseReviewRepository.saveAndFlush(courseReview);

        restCourseReviewMockMvc
                .perform(get(ENTITY_API_URL_ID, courseReview.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(courseReview.getId().intValue()))
                .andExpect(jsonPath("$.review").value(DEFAULT_REVIEW));
    }

    @Test
    @Transactional
    void getNonExistingCourseReview() throws Exception {
        restCourseReviewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCourseReview() throws Exception {
        courseReviewRepository.saveAndFlush(courseReview);

        int databaseSizeBeforeUpdate = courseReviewRepository.findAll().size();

        CourseReview updatedCourseReview = courseReviewRepository.findById(courseReview.getId()).get();

        em.detach(updatedCourseReview);
        updatedCourseReview.setReview(UPDATED_REVIEW);
        CourseReviewDTO courseReviewDTO = courseReviewMapper.toDto(updatedCourseReview);

        restCourseReviewMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, courseReviewDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(courseReviewDTO))
                )
                .andExpect(status().isOk());

        List<CourseReview> courseReviewList = courseReviewRepository.findAll();
        assertThat(courseReviewList).hasSize(databaseSizeBeforeUpdate);
        CourseReview testCourseReview = courseReviewList.get(courseReviewList.size() - 1);
        assertThat(testCourseReview.getReview()).isEqualTo(UPDATED_REVIEW);
    }

    @Test
    @Transactional
    void putNonExistingCourseReview() throws Exception {
        int databaseSizeBeforeUpdate = courseReviewRepository.findAll().size();
        courseReview.setId(count.incrementAndGet());

        CourseReviewDTO courseReviewDTO = courseReviewMapper.toDto(courseReview);

        restCourseReviewMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, courseReviewDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(courseReviewDTO))
                )
                .andExpect(status().isBadRequest());

        List<CourseReview> courseReviewList = courseReviewRepository.findAll();
        assertThat(courseReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourseReview() throws Exception {
        int databaseSizeBeforeUpdate = courseReviewRepository.findAll().size();
        courseReview.setId(count.incrementAndGet());

        CourseReviewDTO courseReviewDTO = courseReviewMapper.toDto(courseReview);

        restCourseReviewMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, count.incrementAndGet())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(courseReviewDTO))
                )
                .andExpect(status().isBadRequest());

        List<CourseReview> courseReviewList = courseReviewRepository.findAll();
        assertThat(courseReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourseReview() throws Exception {
        int databaseSizeBeforeUpdate = courseReviewRepository.findAll().size();
        courseReview.setId(count.incrementAndGet());

        CourseReviewDTO courseReviewDTO = courseReviewMapper.toDto(courseReview);

        restCourseReviewMockMvc
                .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(courseReviewDTO)))
                .andExpect(status().isMethodNotAllowed());

        List<CourseReview> courseReviewList = courseReviewRepository.findAll();
        assertThat(courseReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourseReview() throws Exception {
        courseReviewRepository.saveAndFlush(courseReview);

        int databaseSizeBeforeDelete = courseReviewRepository.findAll().size();

        restCourseReviewMockMvc
                .perform(delete(ENTITY_API_URL_ID, courseReview.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        List<CourseReview> courseReviewList = courseReviewRepository.findAll();
        assertThat(courseReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
