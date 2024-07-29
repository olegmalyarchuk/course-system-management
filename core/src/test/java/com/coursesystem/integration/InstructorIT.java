package com.coursesystem.integration;

import com.coursesystem.dto.InstructorDTO;
import com.coursesystem.mapper.InstructorMapper;
import com.coursesystem.model.Instructor;
import com.coursesystem.model.UserRole;
import com.coursesystem.repository.InstructorRepository;
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
class InstructorIT {

    private static final String DEFAULT_USER_ROLE = UserRole.INSTRUCTOR.name();

    private static final String ENTITY_API_URL = "/api/v1/instructors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorMapper instructorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstructorMockMvc;

    private Instructor instructor;

    @Autowired
    private TestUtil testUtil;

    public Instructor createEntity(EntityManager em) {
        Instructor instructor = testUtil.createTestInstructorEntity();
        return instructor;
    }

    @BeforeEach
    public void initTest() {
        instructor = createEntity(em);
    }

    @Test
    @Transactional
    void createInstructor() throws Exception {
        int databaseSizeBeforeCreate = instructorRepository.findAll().size();

        InstructorDTO instructorDTO = instructorMapper.toDto(instructor);
        restInstructorMockMvc
                .perform(post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(instructorDTO)))
                .andExpect(status().isCreated());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeCreate + 1);
        Instructor testInstructor = instructorList.get(instructorList.size() - 1);
        assertThat(testInstructor.getUser().getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testInstructor.getUser().getSecondName()).isEqualTo(DEFAULT_SECOND_INSTRUCTOR_NAME);
        assertThat(testInstructor.getUser().getEmail()).isEqualTo(DEFAULT_INSTRUCTOR_EMAIL);
        assertThat(testInstructor.getUser().getUserRoles()).contains(DEFAULT_USER_ROLE);
    }

    @Test
    @Transactional
    void createInstructorWithExistingId() throws Exception {
        instructor.setId(1L);
        InstructorDTO instructorDTO = instructorMapper.toDto(instructor);

        int databaseSizeBeforeCreate = instructorRepository.findAll().size();

        restInstructorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(instructorDTO)))
                .andExpect(status().isBadRequest());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = instructorRepository.findAll().size();

        instructor.setUser(null);

        InstructorDTO instructorDTO = instructorMapper.toDto(instructor);

        restInstructorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(instructorDTO)))
                .andExpect(status().isBadRequest());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInstructors() throws Exception {
        instructorRepository.saveAndFlush(instructor);

        restInstructorMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instructor.getId().intValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_INSTRUCTOR_EMAIL)))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_INSTRUCTOR_NAME)))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(instructor.getUser().getId().intValue())));
    }

    @Test
    @Transactional
    void getInstructor() throws Exception {
        instructorRepository.saveAndFlush(instructor);

        restInstructorMockMvc
                .perform(get(ENTITY_API_URL_ID, instructor.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(instructor.getId().intValue()))
                .andExpect(jsonPath("$.email").value(DEFAULT_INSTRUCTOR_EMAIL))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.secondName").value(DEFAULT_SECOND_INSTRUCTOR_NAME))
                .andExpect(jsonPath("$.userId").value(instructor.getUser().getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingInstructor() throws Exception {
        restInstructorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNonExistingInstructor() throws Exception {
        int databaseSizeBeforeUpdate = instructorRepository.findAll().size();
        instructor.setId(count.incrementAndGet());

        InstructorDTO instructorDTO = instructorMapper.toDto(instructor);

        restInstructorMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, instructorDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(instructorDTO))
                )
                .andExpect(status().isBadRequest());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInstructor() throws Exception {
        int databaseSizeBeforeUpdate = instructorRepository.findAll().size();
        instructor.setId(count.incrementAndGet());

        InstructorDTO instructorDTO = instructorMapper.toDto(instructor);

        restInstructorMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, count.incrementAndGet())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(instructorDTO))
                )
                .andExpect(status().isBadRequest());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInstructor() throws Exception {
        int databaseSizeBeforeUpdate = instructorRepository.findAll().size();
        instructor.setId(count.incrementAndGet());

        InstructorDTO instructorDTO = instructorMapper.toDto(instructor);

        restInstructorMockMvc
                .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(instructorDTO)))
                .andExpect(status().isMethodNotAllowed());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInstructor() throws Exception {
        instructorRepository.saveAndFlush(instructor);

        int databaseSizeBeforeDelete = instructorRepository.findAll().size();

        restInstructorMockMvc
                .perform(delete(ENTITY_API_URL_ID, instructor.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        List<Instructor> instructorList = instructorRepository.findAll();
        assertThat(instructorList).hasSize(databaseSizeBeforeDelete - 1);
    }

}
