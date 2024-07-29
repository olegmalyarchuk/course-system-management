package com.coursesystem.integration;

import com.coursesystem.dto.StudentDTO;
import com.coursesystem.mapper.StudentMapper;
import com.coursesystem.model.Student;
import com.coursesystem.model.UserRole;
import com.coursesystem.repository.StudentRepository;
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

import static com.coursesystem.util.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
class StudentIT {

    private static final String DEFAULT_USER_ROLE = UserRole.STUDENT.name();

    private static final String ENTITY_API_URL = "/api/v1/students";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentMockMvc;

    private Student student;

    @Autowired
    private TestUtil testUtil;

    public Student createEntity(EntityManager em) {
        Student student = testUtil.createTestStudentEntity();
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        StudentDTO studentDTO = studentMapper.toDto(student);
        restStudentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(studentDTO)))
                .andExpect(status().isCreated());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getUser().getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStudent.getUser().getSecondName()).isEqualTo(DEFAULT_SECOND_STUDENT_NAME);
        assertThat(testStudent.getUser().getEmail()).isEqualTo(DEFAULT_STUDENT_EMAIL);
        assertThat(testStudent.getUser().getUserRoles()).contains(DEFAULT_USER_ROLE);
    }

    @Test
    @Transactional
    void createStudentWithExistingId() throws Exception {
        student.setId(1L);
        StudentDTO studentDTO = studentMapper.toDto(student);

        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        restStudentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(studentDTO)))
                .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStudents() throws Exception {
        studentRepository.saveAndFlush(student);

        restStudentMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_STUDENT_EMAIL)))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_STUDENT_NAME)))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(student.getUser().getId().intValue())));
    }

    @Test
    @Transactional
    void getStudent() throws Exception {
        studentRepository.saveAndFlush(student);

        restStudentMockMvc
                .perform(get(ENTITY_API_URL_ID, student.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(student.getId().intValue()))
                .andExpect(jsonPath("$.email").value(DEFAULT_STUDENT_EMAIL))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.secondName").value(DEFAULT_SECOND_STUDENT_NAME))
                .andExpect(jsonPath("$.userId").value(student.getUser().getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingStudent() throws Exception {
        restStudentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void testNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, studentDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(studentDTO))
                )
                .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, count.incrementAndGet())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(studentDTO))
                )
                .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void testWithMissingIdPathParamStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
                .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(studentDTO)))
                .andExpect(status().isMethodNotAllowed());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudent() throws Exception {
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        restStudentMockMvc
                .perform(delete(ENTITY_API_URL_ID, student.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }

}
