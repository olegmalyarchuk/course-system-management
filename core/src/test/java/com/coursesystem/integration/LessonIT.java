package com.coursesystem.integration;

import com.coursesystem.dto.LessonDTO;
import com.coursesystem.mapper.LessonMapper;
import com.coursesystem.model.Course;
import com.coursesystem.model.Lesson;
import com.coursesystem.repository.LessonRepository;
import com.coursesystem.util.JsonUtil;
import com.coursesystem.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
class LessonIT {

    private static final String DEFAULT_NAME = "DEFAULT_NAME";

    private static final Short DEFAULT_NUMBER_OF_LESSONS = 10;

    private static final Short DEFAULT_LESSON_NUMBER = 1;
    private static final Short DEFAULT_MARK = 2;
    private static final Short UPDATED_MARK = 5;

    private static final String ENTITY_API_URL = "/api/v1/lessons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonMapper lessonMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLessonMockMvc;

    @Autowired
    private TestUtil testUtil;

    private Lesson lesson;

    public Lesson createEntity() {
       var student = testUtil.createTestStudentWithCourse();
        Lesson lesson = Lesson.builder()
                .lessonNumber(DEFAULT_LESSON_NUMBER)
                .mark(DEFAULT_MARK)
                .course(student.getCourses().stream().findAny().get())
                .student(student)
                .build();
        return lesson;
    }

    @BeforeEach
    public void initTest() {
        lesson = createEntity();
    }

    @Test
    @Transactional
    void createLesson() throws Exception {
        int databaseSizeBeforeCreate = lessonRepository.findAll().size();
        LessonDTO lessonDTO = lessonMapper.toDto(lesson);
        restLessonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(lessonDTO)))
                .andExpect(status().isCreated());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeCreate + 1);
        Lesson testLesson = lessonList.get(lessonList.size() - 1);
        assertThat(testLesson.getLessonNumber()).isEqualTo(DEFAULT_LESSON_NUMBER);
        assertThat(testLesson.getMark()).isEqualTo(DEFAULT_MARK);
    }

    @Test
    @Transactional
    void createLessonWithExistingId() throws Exception {
        lesson.setId(1L);
        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        int databaseSizeBeforeCreate = lessonRepository.findAll().size();

        restLessonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(lessonDTO)))
                .andExpect(status().isBadRequest());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLessonNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonRepository.findAll().size();

        lesson.setLessonNumber(null);

        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        restLessonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(lessonDTO)))
                .andExpect(status().isBadRequest());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLessons() throws Exception {
        lessonRepository.saveAndFlush(lesson);

        restLessonMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lesson.getId().intValue())))
                .andExpect(jsonPath("$.[*].lessonNumber").value(hasItem(DEFAULT_LESSON_NUMBER.intValue())))
                .andExpect(jsonPath("$.[*].mark").value(hasItem(DEFAULT_MARK.intValue())))
                .andExpect(jsonPath("$.[*].studentId").value(hasItem(lesson.getStudent().getId().intValue())))
                .andExpect(jsonPath("$.[*].courseId").value(hasItem(lesson.getCourse().getId().intValue())));
    }

    @Test
    @Transactional
    void getLesson() throws Exception {
        lessonRepository.saveAndFlush(lesson);

        restLessonMockMvc
                .perform(get(ENTITY_API_URL_ID, lesson.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(lesson.getId().intValue()))
                .andExpect(jsonPath("$.lessonNumber").value(DEFAULT_LESSON_NUMBER.intValue()))
                .andExpect(jsonPath("$.mark").value(DEFAULT_MARK.intValue()))
                .andExpect(jsonPath("$.studentId").value(lesson.getStudent().getId().intValue()))
                .andExpect(jsonPath("$.courseId").value(lesson.getCourse().getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingLesson() throws Exception {
        restLessonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLesson() throws Exception {
        lessonRepository.saveAndFlush(lesson);

        int databaseSizeBeforeUpdate = lessonRepository.findAll().size();

        Lesson updatedLesson = lessonRepository.findById(lesson.getId()).get();

        em.detach(updatedLesson);
        updatedLesson.setMark(UPDATED_MARK);
        LessonDTO lessonDTO = lessonMapper.toDto(updatedLesson);

        restLessonMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, lessonDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(lessonDTO))
                )
                .andExpect(status().isOk());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeUpdate);
        Lesson testLesson = lessonList.get(lessonList.size() - 1);
        assertThat(testLesson.getLessonNumber()).isEqualTo(DEFAULT_LESSON_NUMBER);
        assertThat(testLesson.getMark()).isEqualTo(UPDATED_MARK);
    }

    @Test
    @Transactional
    void putNonExistingLesson() throws Exception {
        int databaseSizeBeforeUpdate = lessonRepository.findAll().size();
        lesson.setId(count.incrementAndGet());

        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        restLessonMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, lessonDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(lessonDTO))
                )
                .andExpect(status().isBadRequest());

        // Validate the Lesson in the database
        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLesson() throws Exception {
        int databaseSizeBeforeUpdate = lessonRepository.findAll().size();
        lesson.setId(count.incrementAndGet());

        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        restLessonMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, count.incrementAndGet())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(lessonDTO))
                )
                .andExpect(status().isBadRequest());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLesson() throws Exception {
        int databaseSizeBeforeUpdate = lessonRepository.findAll().size();
        lesson.setId(count.incrementAndGet());

        LessonDTO lessonDTO = lessonMapper.toDto(lesson);

        restLessonMockMvc
                .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(lessonDTO)))
                .andExpect(status().isMethodNotAllowed());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLesson() throws Exception {
        lessonRepository.saveAndFlush(lesson);

        int databaseSizeBeforeDelete = lessonRepository.findAll().size();

        restLessonMockMvc
                .perform(delete(ENTITY_API_URL_ID, lesson.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        List<Lesson> lessonList = lessonRepository.findAll();
        assertThat(lessonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    public static Course createCourse() {
        Course course = Course.builder()
                .name(DEFAULT_NAME)
                .numberOfLessons(DEFAULT_NUMBER_OF_LESSONS)
                .build();
        return course;
    }
}
