package com.coursesystem.unit.controller;

import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.controller.StudentController;
import com.coursesystem.dto.StudentDTO;
import com.coursesystem.dto.vo.CourseVO;
import com.coursesystem.model.User;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.StudentService;
import com.coursesystem.service.impl.StudentServiceImpl;
import com.coursesystem.service.impl.UserServiceImpl;
import com.coursesystem.mapper.StudentMapperImpl;
import com.coursesystem.mapper.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {StudentController.class})
@ExtendWith(SpringExtension.class)
class StudentControllerTest {
    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentController studentController;

    @MockBean
    private StudentService studentService;


    @Test
    void testCreateStudent() throws URISyntaxException {
        StudentRepository studentRepository = mock(StudentRepository.class);
        StudentMapperImpl studentMapper = new StudentMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        StudentController studentController = new StudentController(
                new StudentServiceImpl(studentRepository, studentMapper, userService, new UserMapperImpl()),
                mock(StudentRepository.class));

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setCourses(new HashSet<>());
        studentDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setEmail("jane.doe@example.org");
        studentDTO.setFirstName("Jane");
        studentDTO.setId(123L);
        studentDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setSecondName("Second Name");
        studentDTO.setUserId(123L);
        assertThrows(SystemException.class, () -> studentController.createStudent(studentDTO));
    }

    @Test
    void testCreateStudent2() throws URISyntaxException {
        StudentRepository studentRepository = mock(StudentRepository.class);
        StudentMapperImpl studentMapper = new StudentMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        StudentController studentController = new StudentController(
                new StudentServiceImpl(studentRepository, studentMapper, userService, new UserMapperImpl()),
                mock(StudentRepository.class));
        StudentDTO studentDTO = mock(StudentDTO.class);
        when(studentDTO.getId()).thenReturn(123L);
        doNothing().when(studentDTO).setCreatedBy((String) any());
        doNothing().when(studentDTO).setCreatedAt((LocalDateTime) any());
        doNothing().when(studentDTO).setUpdatedBy((String) any());
        doNothing().when(studentDTO).setUpdatedAt((LocalDateTime) any());
        doNothing().when(studentDTO).setCourses((java.util.Set<CourseVO>) any());
        doNothing().when(studentDTO).setEmail((String) any());
        doNothing().when(studentDTO).setFirstName((String) any());
        doNothing().when(studentDTO).setId((Long) any());
        doNothing().when(studentDTO).setSecondName((String) any());
        doNothing().when(studentDTO).setUserId((Long) any());
        studentDTO.setCourses(new HashSet<>());
        studentDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setEmail("jane.doe@example.org");
        studentDTO.setFirstName("Jane");
        studentDTO.setId(123L);
        studentDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setSecondName("Second Name");
        studentDTO.setUserId(123L);
        assertThrows(SystemException.class, () -> studentController.createStudent(studentDTO));
        verify(studentDTO).getId();
        verify(studentDTO).setCreatedBy((String) any());
        verify(studentDTO).setCreatedAt((LocalDateTime) any());
        verify(studentDTO).setUpdatedBy((String) any());
        verify(studentDTO).setUpdatedAt((LocalDateTime) any());
        verify(studentDTO).setCourses((java.util.Set<CourseVO>) any());
        verify(studentDTO).setEmail((String) any());
        verify(studentDTO).setFirstName((String) any());
        verify(studentDTO).setId((Long) any());
        verify(studentDTO).setSecondName((String) any());
        verify(studentDTO).setUserId((Long) any());
    }

    @Test
    void testCreateStudent4() throws URISyntaxException {
        User user = new User();
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setPassword("iloveyou");
        user.setSecondName("Second Name");
        user.setUserRoles("User Roles");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getById((Long) any())).thenReturn(user);
        UserMapperImpl userMapper = new UserMapperImpl();
        new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setCourses(new HashSet<>());
        studentDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setEmail("jane.doe@example.org");
        studentDTO.setFirstName("Jane");
        studentDTO.setId(123L);
        studentDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setSecondName("Second Name");
        studentDTO.setUserId(123L);
        StudentService studentService = mock(StudentService.class);
        when(studentService.save((StudentDTO) any())).thenReturn(studentDTO);
        StudentController studentController = new StudentController(studentService, mock(StudentRepository.class));
        StudentDTO studentDTO1 = mock(StudentDTO.class);
        when(studentDTO1.getUserId()).thenReturn(123L);
        when(studentDTO1.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(studentDTO1.getUpdatedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(studentDTO1.getCreatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(studentDTO1.getUpdatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(studentDTO1.getId()).thenReturn(null);
        doNothing().when(studentDTO1).setCreatedBy((String) any());
        doNothing().when(studentDTO1).setCreatedAt((LocalDateTime) any());
        doNothing().when(studentDTO1).setUpdatedBy((String) any());
        doNothing().when(studentDTO1).setUpdatedAt((LocalDateTime) any());
        doNothing().when(studentDTO1).setCourses((java.util.Set<CourseVO>) any());
        doNothing().when(studentDTO1).setEmail((String) any());
        doNothing().when(studentDTO1).setFirstName((String) any());
        doNothing().when(studentDTO1).setId((Long) any());
        doNothing().when(studentDTO1).setSecondName((String) any());
        doNothing().when(studentDTO1).setUserId((Long) any());
        studentDTO1.setCourses(new HashSet<>());
        studentDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO1.setEmail("jane.doe@example.org");
        studentDTO1.setFirstName("Jane");
        studentDTO1.setId(123L);
        studentDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO1.setSecondName("Second Name");
        studentDTO1.setUserId(123L);
        ResponseEntity<StudentDTO> actualCreateStudentResult = studentController.createStudent(studentDTO1);
        assertTrue(actualCreateStudentResult.hasBody());
        assertEquals(3, actualCreateStudentResult.getHeaders().size());
        assertEquals(HttpStatus.CREATED, actualCreateStudentResult.getStatusCode());
        verify(studentService).save((StudentDTO) any());
        verify(studentDTO1).getId();
        verify(studentDTO1).setCreatedBy((String) any());
        verify(studentDTO1).setCreatedAt((LocalDateTime) any());
        verify(studentDTO1).setUpdatedBy((String) any());
        verify(studentDTO1).setUpdatedAt((LocalDateTime) any());
        verify(studentDTO1).setCourses((java.util.Set<CourseVO>) any());
        verify(studentDTO1).setEmail((String) any());
        verify(studentDTO1).setFirstName((String) any());
        verify(studentDTO1).setId((Long) any());
        verify(studentDTO1).setSecondName((String) any());
        verify(studentDTO1).setUserId((Long) any());
    }

    @Test
    void testCreateStudent5() throws URISyntaxException {
        User user = new User(123L, "jane.doe@example.org", "iloveyou", "Jane", "REST request to save Student : {}",
                "REST request to save Student : {}");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setPassword("iloveyou");
        user.setSecondName("Second Name");
        user.setUserRoles("User Roles");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getById((Long) any())).thenReturn(user);
        UserMapperImpl userMapper = new UserMapperImpl();
        new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setCourses(new HashSet<>());
        studentDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setEmail("jane.doe@example.org");
        studentDTO.setFirstName("Jane");
        studentDTO.setId(123L);
        studentDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setSecondName("Second Name");
        studentDTO.setUserId(123L);
        StudentService studentService = mock(StudentService.class);
        when(studentService.save((StudentDTO) any())).thenReturn(studentDTO);
        StudentController studentController = new StudentController(studentService, mock(StudentRepository.class));
        StudentDTO studentDTO1 = mock(StudentDTO.class);
        when(studentDTO1.getUserId()).thenReturn(123L);
        when(studentDTO1.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(studentDTO1.getUpdatedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(studentDTO1.getCreatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(studentDTO1.getUpdatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(studentDTO1.getId()).thenReturn(null);
        doNothing().when(studentDTO1).setCreatedBy((String) any());
        doNothing().when(studentDTO1).setCreatedAt((LocalDateTime) any());
        doNothing().when(studentDTO1).setUpdatedBy((String) any());
        doNothing().when(studentDTO1).setUpdatedAt((LocalDateTime) any());
        doNothing().when(studentDTO1).setCourses((java.util.Set<CourseVO>) any());
        doNothing().when(studentDTO1).setEmail((String) any());
        doNothing().when(studentDTO1).setFirstName((String) any());
        doNothing().when(studentDTO1).setId((Long) any());
        doNothing().when(studentDTO1).setSecondName((String) any());
        doNothing().when(studentDTO1).setUserId((Long) any());
        studentDTO1.setCourses(new HashSet<>());
        studentDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO1.setEmail("jane.doe@example.org");
        studentDTO1.setFirstName("Jane");
        studentDTO1.setId(123L);
        studentDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO1.setSecondName("Second Name");
        studentDTO1.setUserId(123L);
        ResponseEntity<StudentDTO> actualCreateStudentResult = studentController.createStudent(studentDTO1);
        assertTrue(actualCreateStudentResult.hasBody());
        assertEquals(3, actualCreateStudentResult.getHeaders().size());
        assertEquals(HttpStatus.CREATED, actualCreateStudentResult.getStatusCode());
        verify(studentService).save((StudentDTO) any());
        verify(studentDTO1).getId();
        verify(studentDTO1).setCreatedBy((String) any());
        verify(studentDTO1).setCreatedAt((LocalDateTime) any());
        verify(studentDTO1).setUpdatedBy((String) any());
        verify(studentDTO1).setUpdatedAt((LocalDateTime) any());
        verify(studentDTO1).setCourses((java.util.Set<CourseVO>) any());
        verify(studentDTO1).setEmail((String) any());
        verify(studentDTO1).setFirstName((String) any());
        verify(studentDTO1).setId((Long) any());
        verify(studentDTO1).setSecondName((String) any());
        verify(studentDTO1).setUserId((Long) any());
    }

    @Test
    void testUpdateStudent() throws URISyntaxException {
        StudentRepository studentRepository = mock(StudentRepository.class);
        StudentMapperImpl studentMapper = new StudentMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        StudentController studentController = new StudentController(
                new StudentServiceImpl(studentRepository, studentMapper, userService, new UserMapperImpl()),
                mock(StudentRepository.class));

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setCourses(new HashSet<>());
        studentDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setEmail("jane.doe@example.org");
        studentDTO.setFirstName("Jane");
        studentDTO.setId(123L);
        studentDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setSecondName("Second Name");
        studentDTO.setUserId(123L);
        assertThrows(SystemException.class, () -> studentController.updateStudent(1L, studentDTO));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(this.studentService).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/students/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.studentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteStudent2() throws Exception {
        doNothing().when(this.studentService).delete((Long) any());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.studentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteStudent3() throws Exception {
        doNothing().when(this.studentService).delete((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/students/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.studentController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllStudents() throws Exception {
        when(this.studentService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/students");
        MockMvcBuilders.standaloneSetup(this.studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllStudents2() throws Exception {
        when(this.studentService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/students");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.studentController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetStudent() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setCourses(new HashSet<>());
        studentDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setEmail("jane.doe@example.org");
        studentDTO.setFirstName("Jane");
        studentDTO.setId(123L);
        studentDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setSecondName("Second Name");
        studentDTO.setUserId(123L);
        Optional<StudentDTO> ofResult = Optional.of(studentDTO);
        when(this.studentService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/students/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1],"
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"email\":\"jane.doe@example.org\",\"firstName\":\"Jane\""
                                        + ",\"secondName\":\"Second Name\",\"userId\":123,\"courses\":[]}"));
    }

    @Test
    void testGetStudent2() throws Exception {
        when(this.studentService.findOne((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/students/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.studentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetStudent3() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setCourses(new HashSet<>());
        studentDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        studentDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setEmail("jane.doe@example.org");
        studentDTO.setFirstName("Jane");
        studentDTO.setId(123L);
        studentDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        studentDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        studentDTO.setSecondName("Second Name");
        studentDTO.setUserId(123L);
        Optional<StudentDTO> ofResult = Optional.of(studentDTO);
        when(this.studentService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/students/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.studentController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1],"
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"email\":\"jane.doe@example.org\",\"firstName\":\"Jane\""
                                        + ",\"secondName\":\"Second Name\",\"userId\":123,\"courses\":[]}"));
    }
}

