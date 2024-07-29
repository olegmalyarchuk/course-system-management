package com.coursesystem.integration;

import com.coursesystem.controller.dto.RegistrationDTO;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.mapper.UserMapper;
import com.coursesystem.model.Student;
import com.coursesystem.model.User;
import com.coursesystem.model.UserRole;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.util.JsonUtil;
import com.coursesystem.util.TestUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
class UserIT {

    public static final String UPDATED_FIRST_NAME = "NAME";
    public static final String UPDATED_SECOND_NAME = "SURNAME";
    public static final String UPDATED_EMAIL = "namesurname@email.com";

    private static final String ENTITY_API_URL = "/api/v1/users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserMockMvc;

    private User user;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestUtil testUtil;

    public User createEntity(EntityManager em) {
        User user = testUtil.createUserEntity();
        return user;
    }

    @BeforeEach
    public void initTest() {
        user = createEntity(em);
    }

    @Test
    @Transactional
    void createUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();
        final RegistrationDTO registrationDTO = getRegistrationDTO(user);
        restUserMockMvc
                .perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(registrationDTO)))
                .andExpect(status().isOk());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getFirstName()).isEqualTo(TestUtil.DEFAULT_FIRST_NAME);
        assertThat(testUser.getSecondName()).isEqualTo(TestUtil.DEFAULT_SECOND_NAME);
        assertThat(testUser.getEmail()).isEqualTo(TestUtil.DEFAULT_EMAIL);
        assertThat(testUser.getUserRoles()).isEqualTo(JsonUtil.serialize(Collections.singletonList(TestUtil.DEFAULT_USER_ROLE)));
    }

    @Test
    @Transactional
    void createUserWithExistingId() throws Exception {
        final RegistrationDTO registrationDTO = getRegistrationDTO(user);
        registrationDTO.setId(1L);

        int databaseSizeBeforeCreate = userRepository.findAll().size();

        restUserMockMvc
                .perform(post("/api/v1/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(registrationDTO)))
                .andExpect(status().isBadRequest());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRepository.findAll().size();
        user.setEmail(null);

        final RegistrationDTO registrationDTO = getRegistrationDTO(user);

        restUserMockMvc
                .perform(post("/api/v1/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(registrationDTO)))
                .andExpect(status().isBadRequest());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRepository.findAll().size();
        final RegistrationDTO registrationDTO = getRegistrationDTO(user);
        registrationDTO.setPassword(null);

        restUserMockMvc
                .perform(post("/api/v1/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(registrationDTO)))
                .andExpect(status().isBadRequest());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRepository.findAll().size();
        user.setFirstName(null);

        final RegistrationDTO registrationDTO = getRegistrationDTO(user);
        restUserMockMvc
                .perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.serialize(registrationDTO)))
                .andExpect(status().isBadRequest());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSecondNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRepository.findAll().size();
        user.setSecondName(null);

        final RegistrationDTO registrationDTO = getRegistrationDTO(user);

        restUserMockMvc
                .perform(post("/api/v1/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(registrationDTO)))
                .andExpect(status().isBadRequest());

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsers() throws Exception {
        userRepository.saveAndFlush(user);
        
        restUserMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(user.getId().intValue())))
                .andExpect(jsonPath("$.[*].email").value(Matchers.hasItem(TestUtil.DEFAULT_EMAIL)))
                .andExpect(jsonPath("$.[*].firstName").value(Matchers.hasItem(TestUtil.DEFAULT_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].secondName").value(Matchers.hasItem(TestUtil.DEFAULT_SECOND_NAME)));
    }

    @Test
    @Transactional
    void getUser() throws Exception {
        userRepository.saveAndFlush(user);
        
        restUserMockMvc
                .perform(get(ENTITY_API_URL_ID, user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(user.getId().intValue()))
                .andExpect(jsonPath("$.email").value(TestUtil.DEFAULT_EMAIL))
                .andExpect(jsonPath("$.firstName").value(TestUtil.DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.secondName").value(TestUtil.DEFAULT_SECOND_NAME));
    }

    @Test
    @Transactional
    void getNonExistingUser() throws Exception {
        restUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUser() throws Exception {
        userRepository.saveAndFlush(user);

        int databaseSizeBeforeUpdate = userRepository.findAll().size();
        
        User updatedUser = userRepository.findById(user.getId()).get();
       
        em.detach(updatedUser);
        updatedUser.setFirstName(UPDATED_FIRST_NAME);
        updatedUser.setSecondName(UPDATED_SECOND_NAME);
        updatedUser.setEmail(UPDATED_EMAIL);
        UserDTO userDTO = userMapper.toDto(updatedUser);

        restUserMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, userDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(userDTO))
                )
                .andExpect(status().isOk());
        
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUser.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUser.getUserRoles()).isEqualTo(JsonUtil.serialize(Collections.singletonList(TestUtil.DEFAULT_USER_ROLE)));
    }

    @Test
    @Transactional
    void putNonExistingUser() throws Exception {
        int databaseSizeBeforeUpdate = userRepository.findAll().size();
        user.setId(count.incrementAndGet());
        
        UserDTO userDTO = userMapper.toDto(user);
        
        restUserMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, userDTO.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(userDTO))
                )
                .andExpect(status().isBadRequest());
        
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUser() throws Exception {
        int databaseSizeBeforeUpdate = userRepository.findAll().size();
        user.setId(count.incrementAndGet());
        
        UserDTO userDTO = userMapper.toDto(user);
        
        restUserMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, count.incrementAndGet())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.serialize(userDTO))
                )
                .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUser() throws Exception {
        int databaseSizeBeforeUpdate = userRepository.findAll().size();
        user.setId(count.incrementAndGet());
        
        UserDTO userDTO = userMapper.toDto(user);
        
        restUserMockMvc
                .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.serialize(userDTO)))
                .andExpect(status().isMethodNotAllowed());
        
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUser() throws Exception {
        userRepository.saveAndFlush(user);

        int databaseSizeBeforeDelete = userRepository.findAll().size();
        
        restUserMockMvc
                .perform(delete(ENTITY_API_URL_ID, user.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    }

//    @Test
//    void adminCanAssignRoleToUser() throws Exception {
//        User user = testUtil.createTestUser();
//
//        ResultActions resultActions = restUserMockMvc.perform(put("/api/v1/users/{userId}/role", user.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(UserRole.STUDENT.name()));
//
//        // THEN role is set for user
//        resultActions.andExpect(status().isOk());
//    }

    @Test
    void adminCantAssignRoleToUser_whenRoleAlreadySet() throws Exception {
        Student student = testUtil.createTestStudentWithCourse();

        ResultActions resultActions = restUserMockMvc.perform(put("/api/users/" + student.getId() + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserRole.STUDENT.name()));
        
        resultActions.andExpect(status().is4xxClientError());

        Student savedStudent = studentRepository.getById(student.getId());
        assertNotNull(savedStudent);
        assertTrue(savedStudent.getUser().getUserRoles().contains(UserRole.STUDENT.name()));
    }

    @Test
    @WithMockUser(roles = {"STUDENT"})
    void nonUserUserCantAssignRoleToUser() throws Exception {
        Student student = testUtil.createTestStudentWithCourse();
        
        ResultActions resultActions = restUserMockMvc.perform(put("/api/v1/users/" + student.getUser().getId() + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserRole.STUDENT.name()));
        
        resultActions.andExpect(status().isForbidden());
    }

    private RegistrationDTO getRegistrationDTO(User user) {
        final RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail(user.getEmail());
        registrationDTO.setPassword(TestUtil.DEFAULT_PASSWORD);
        registrationDTO.setFirstName(user.getFirstName());
        registrationDTO.setSecondName(user.getSecondName());
        registrationDTO.setUserRoles(JsonUtil.deserialize(user.getUserRoles(), new TypeReference<Set<UserRole>>() {
        }));
        return registrationDTO;
    }

}
