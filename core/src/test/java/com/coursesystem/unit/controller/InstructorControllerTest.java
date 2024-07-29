package com.coursesystem.unit.controller;

import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.controller.InstructorController;
import com.coursesystem.dto.InstructorDTO;
import com.coursesystem.dto.vo.CourseVO;
import com.coursesystem.model.User;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.InstructorService;
import com.coursesystem.service.impl.InstructorServiceImpl;
import com.coursesystem.service.impl.UserServiceImpl;
import com.coursesystem.mapper.InstructorMapperImpl;
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

@ContextConfiguration(classes = {InstructorController.class})
@ExtendWith(SpringExtension.class)
class InstructorControllerTest {
    @MockBean
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorController instructorController;

    @MockBean
    private InstructorService instructorService;

    @Test
    void testCreateInstructor() throws URISyntaxException {
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        InstructorMapperImpl instructorMapper = new InstructorMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        InstructorController instructorController = new InstructorController(
                new InstructorServiceImpl(instructorRepository, instructorMapper, userService, new UserMapperImpl()),
                mock(InstructorRepository.class));

        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setCourses(new HashSet<>());
        instructorDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructorDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setEmail("jane.doe@example.org");
        instructorDTO.setFirstName("Jane");
        instructorDTO.setId(123L);
        instructorDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructorDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setSecondName("Second Name");
        instructorDTO.setUserId(123L);
        assertThrows(SystemException.class, () -> instructorController.createInstructor(instructorDTO));
    }

    @Test
    void testCreateInstructor2() throws URISyntaxException {
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        InstructorMapperImpl instructorMapper = new InstructorMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        InstructorController instructorController = new InstructorController(
                new InstructorServiceImpl(instructorRepository, instructorMapper, userService, new UserMapperImpl()),
                mock(InstructorRepository.class));
        InstructorDTO instructorDTO = mock(InstructorDTO.class);
        when(instructorDTO.getId()).thenReturn(123L);
        doNothing().when(instructorDTO).setCreatedBy((String) any());
        doNothing().when(instructorDTO).setCreatedAt((LocalDateTime) any());
        doNothing().when(instructorDTO).setUpdatedBy((String) any());
        doNothing().when(instructorDTO).setUpdatedAt((LocalDateTime) any());
        doNothing().when(instructorDTO).setCourses((java.util.Set<CourseVO>) any());
        doNothing().when(instructorDTO).setEmail((String) any());
        doNothing().when(instructorDTO).setFirstName((String) any());
        doNothing().when(instructorDTO).setId((Long) any());
        doNothing().when(instructorDTO).setSecondName((String) any());
        doNothing().when(instructorDTO).setUserId((Long) any());
        instructorDTO.setCourses(new HashSet<>());
        instructorDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructorDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setEmail("jane.doe@example.org");
        instructorDTO.setFirstName("Jane");
        instructorDTO.setId(123L);
        instructorDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructorDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setSecondName("Second Name");
        instructorDTO.setUserId(123L);
        assertThrows(SystemException.class, () -> instructorController.createInstructor(instructorDTO));
        verify(instructorDTO).getId();
        verify(instructorDTO).setCreatedBy((String) any());
        verify(instructorDTO).setCreatedAt((LocalDateTime) any());
        verify(instructorDTO).setUpdatedBy((String) any());
        verify(instructorDTO).setUpdatedAt((LocalDateTime) any());
        verify(instructorDTO).setCourses((java.util.Set<CourseVO>) any());
        verify(instructorDTO).setEmail((String) any());
        verify(instructorDTO).setFirstName((String) any());
        verify(instructorDTO).setId((Long) any());
        verify(instructorDTO).setSecondName((String) any());
        verify(instructorDTO).setUserId((Long) any());
    }

    @Test
    void testCreateInstructor4() throws URISyntaxException {
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

        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setCourses(new HashSet<>());
        instructorDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructorDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setEmail("jane.doe@example.org");
        instructorDTO.setFirstName("Jane");
        instructorDTO.setId(123L);
        instructorDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructorDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setSecondName("Second Name");
        instructorDTO.setUserId(123L);
        InstructorService instructorService = mock(InstructorService.class);
        when(instructorService.save((InstructorDTO) any())).thenReturn(instructorDTO);
        InstructorController instructorController = new InstructorController(instructorService, mock(InstructorRepository.class));
        InstructorDTO instructorDTO1 = mock(InstructorDTO.class);
        when(instructorDTO1.getUserId()).thenReturn(123L);
        when(instructorDTO1.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(instructorDTO1.getUpdatedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(instructorDTO1.getCreatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(instructorDTO1.getUpdatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(instructorDTO1.getId()).thenReturn(null);
        doNothing().when(instructorDTO1).setCreatedBy((String) any());
        doNothing().when(instructorDTO1).setCreatedAt((LocalDateTime) any());
        doNothing().when(instructorDTO1).setUpdatedBy((String) any());
        doNothing().when(instructorDTO1).setUpdatedAt((LocalDateTime) any());
        doNothing().when(instructorDTO1).setCourses((java.util.Set<CourseVO>) any());
        doNothing().when(instructorDTO1).setEmail((String) any());
        doNothing().when(instructorDTO1).setFirstName((String) any());
        doNothing().when(instructorDTO1).setId((Long) any());
        doNothing().when(instructorDTO1).setSecondName((String) any());
        doNothing().when(instructorDTO1).setUserId((Long) any());
        instructorDTO1.setCourses(new HashSet<>());
        instructorDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructorDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO1.setEmail("jane.doe@example.org");
        instructorDTO1.setFirstName("Jane");
        instructorDTO1.setId(123L);
        instructorDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructorDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO1.setSecondName("Second Name");
        instructorDTO1.setUserId(123L);
        ResponseEntity<InstructorDTO> actualCreateInstructorResult = instructorController.createInstructor(instructorDTO1);
        assertTrue(actualCreateInstructorResult.hasBody());
        assertEquals(3, actualCreateInstructorResult.getHeaders().size());
        assertEquals(HttpStatus.CREATED, actualCreateInstructorResult.getStatusCode());
        verify(instructorService).save((InstructorDTO) any());
        verify(instructorDTO1).getId();
        verify(instructorDTO1).setCreatedBy((String) any());
        verify(instructorDTO1).setCreatedAt((LocalDateTime) any());
        verify(instructorDTO1).setUpdatedBy((String) any());
        verify(instructorDTO1).setUpdatedAt((LocalDateTime) any());
        verify(instructorDTO1).setCourses((java.util.Set<CourseVO>) any());
        verify(instructorDTO1).setEmail((String) any());
        verify(instructorDTO1).setFirstName((String) any());
        verify(instructorDTO1).setId((Long) any());
        verify(instructorDTO1).setSecondName((String) any());
        verify(instructorDTO1).setUserId((Long) any());
    }

    @Test
    void testCreateInstructor5() throws URISyntaxException {
        User user = new User(123L, "jane.doe@example.org", "iloveyou", "Jane", "REST request to save Instructor : {}",
                "REST request to save Instructor : {}");
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

        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setCourses(new HashSet<>());
        instructorDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructorDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setEmail("jane.doe@example.org");
        instructorDTO.setFirstName("Jane");
        instructorDTO.setId(123L);
        instructorDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructorDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setSecondName("Second Name");
        instructorDTO.setUserId(123L);
        InstructorService instructorService = mock(InstructorService.class);
        when(instructorService.save((InstructorDTO) any())).thenReturn(instructorDTO);
        InstructorController instructorController = new InstructorController(instructorService, mock(InstructorRepository.class));
        InstructorDTO instructorDTO1 = mock(InstructorDTO.class);
        when(instructorDTO1.getUserId()).thenReturn(123L);
        when(instructorDTO1.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(instructorDTO1.getUpdatedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(instructorDTO1.getCreatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(instructorDTO1.getUpdatedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(instructorDTO1.getId()).thenReturn(null);
        doNothing().when(instructorDTO1).setCreatedBy((String) any());
        doNothing().when(instructorDTO1).setCreatedAt((LocalDateTime) any());
        doNothing().when(instructorDTO1).setUpdatedBy((String) any());
        doNothing().when(instructorDTO1).setUpdatedAt((LocalDateTime) any());
        doNothing().when(instructorDTO1).setCourses((java.util.Set<CourseVO>) any());
        doNothing().when(instructorDTO1).setEmail((String) any());
        doNothing().when(instructorDTO1).setFirstName((String) any());
        doNothing().when(instructorDTO1).setId((Long) any());
        doNothing().when(instructorDTO1).setSecondName((String) any());
        doNothing().when(instructorDTO1).setUserId((Long) any());
        instructorDTO1.setCourses(new HashSet<>());
        instructorDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructorDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO1.setEmail("jane.doe@example.org");
        instructorDTO1.setFirstName("Jane");
        instructorDTO1.setId(123L);
        instructorDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructorDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO1.setSecondName("Second Name");
        instructorDTO1.setUserId(123L);
        ResponseEntity<InstructorDTO> actualCreateInstructorResult = instructorController.createInstructor(instructorDTO1);
        assertTrue(actualCreateInstructorResult.hasBody());
        assertEquals(3, actualCreateInstructorResult.getHeaders().size());
        assertEquals(HttpStatus.CREATED, actualCreateInstructorResult.getStatusCode());
        verify(instructorService).save((InstructorDTO) any());
        verify(instructorDTO1).getId();
        verify(instructorDTO1).setCreatedBy((String) any());
        verify(instructorDTO1).setCreatedAt((LocalDateTime) any());
        verify(instructorDTO1).setUpdatedBy((String) any());
        verify(instructorDTO1).setUpdatedAt((LocalDateTime) any());
        verify(instructorDTO1).setCourses((java.util.Set<CourseVO>) any());
        verify(instructorDTO1).setEmail((String) any());
        verify(instructorDTO1).setFirstName((String) any());
        verify(instructorDTO1).setId((Long) any());
        verify(instructorDTO1).setSecondName((String) any());
        verify(instructorDTO1).setUserId((Long) any());
    }

    @Test
    void testUpdateInstructor() throws URISyntaxException {
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        InstructorMapperImpl instructorMapper = new InstructorMapperImpl();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        InstructorController instructorController = new InstructorController(
                new InstructorServiceImpl(instructorRepository, instructorMapper, userService, new UserMapperImpl()),
                mock(InstructorRepository.class));

        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setCourses(new HashSet<>());
        instructorDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructorDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setEmail("jane.doe@example.org");
        instructorDTO.setFirstName("Jane");
        instructorDTO.setId(123L);
        instructorDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructorDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setSecondName("Second Name");
        instructorDTO.setUserId(123L);
        assertThrows(SystemException.class, () -> instructorController.updateInstructor(1L, instructorDTO));
    }

    @Test
    void testDeleteInstructor() throws Exception {
        doNothing().when(this.instructorService).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/instructors/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.instructorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteInstructor2() throws Exception {
        doNothing().when(this.instructorService).delete((Long) any());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.instructorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteInstructor3() throws Exception {
        doNothing().when(this.instructorService).delete((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/instructors/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.instructorController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllInstructors() throws Exception {
        when(this.instructorService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/instructors");
        MockMvcBuilders.standaloneSetup(this.instructorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllInstructors2() throws Exception {
        when(this.instructorService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/instructors");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.instructorController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetInstructor() throws Exception {
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setCourses(new HashSet<>());
        instructorDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructorDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setEmail("jane.doe@example.org");
        instructorDTO.setFirstName("Jane");
        instructorDTO.setId(123L);
        instructorDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructorDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setSecondName("Second Name");
        instructorDTO.setUserId(123L);
        Optional<InstructorDTO> ofResult = Optional.of(instructorDTO);
        when(this.instructorService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/instructors/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.instructorController)
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
    void testGetInstructor2() throws Exception {
        when(this.instructorService.findOne((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/instructors/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.instructorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetInstructor3() throws Exception {
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setCourses(new HashSet<>());
        instructorDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructorDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setEmail("jane.doe@example.org");
        instructorDTO.setFirstName("Jane");
        instructorDTO.setId(123L);
        instructorDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructorDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructorDTO.setSecondName("Second Name");
        instructorDTO.setUserId(123L);
        Optional<InstructorDTO> ofResult = Optional.of(instructorDTO);
        when(this.instructorService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/instructors/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.instructorController)
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

