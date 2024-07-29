package com.coursesystem.unit.controller;

import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.controller.UserController;
import com.coursesystem.controller.dto.RegistrationDTO;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.model.UserRole;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.SecurityService;
import com.coursesystem.service.UserService;
import com.coursesystem.service.impl.UserServiceImpl;
import com.coursesystem.mapper.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private SecurityService securityService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(this.userService).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testRegisterAccount() throws URISyntaxException {
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        UserRepository userRepository1 = mock(UserRepository.class);
        UserController userController = new UserController(userService, userRepository1);

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        registrationDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        registrationDTO.setEmail("jane.doe@example.org");
        registrationDTO.setFirstName("Jane");
        registrationDTO.setId(123L);
        registrationDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        registrationDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        registrationDTO.setPassword("iloveyou");
        registrationDTO.setSecondName("Second Name");
        registrationDTO.setUserRoles(new HashSet<>());
        assertThrows(SystemException.class, () -> userController.registerAccount(registrationDTO));
    }

    @Test
    void testRegisterAccount2() throws URISyntaxException {
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        UserRepository userRepository1 = mock(UserRepository.class);
        UserController userController = new UserController(userService, userRepository1);
        RegistrationDTO registrationDTO = mock(RegistrationDTO.class);
        when(registrationDTO.getId()).thenReturn(123L);
        doNothing().when(registrationDTO).setCreatedBy((String) any());
        doNothing().when(registrationDTO).setCreatedAt((LocalDateTime) any());
        doNothing().when(registrationDTO).setUpdatedBy((String) any());
        doNothing().when(registrationDTO).setUpdatedAt((LocalDateTime) any());
        doNothing().when(registrationDTO).setEmail((String) any());
        doNothing().when(registrationDTO).setFirstName((String) any());
        doNothing().when(registrationDTO).setId((Long) any());
        doNothing().when(registrationDTO).setSecondName((String) any());
        doNothing().when(registrationDTO).setUserRoles((java.util.Set<UserRole>) any());
        doNothing().when(registrationDTO).setPassword((String) any());
        registrationDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        registrationDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        registrationDTO.setEmail("jane.doe@example.org");
        registrationDTO.setFirstName("Jane");
        registrationDTO.setId(123L);
        registrationDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        registrationDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        registrationDTO.setPassword("iloveyou");
        registrationDTO.setSecondName("Second Name");
        registrationDTO.setUserRoles(new HashSet<>());
        assertThrows(SystemException.class, () -> userController.registerAccount(registrationDTO));
        verify(registrationDTO).getId();
        verify(registrationDTO).setCreatedBy((String) any());
        verify(registrationDTO).setCreatedAt((LocalDateTime) any());
        verify(registrationDTO).setUpdatedBy((String) any());
        verify(registrationDTO).setUpdatedAt((LocalDateTime) any());
        verify(registrationDTO).setEmail((String) any());
        verify(registrationDTO).setFirstName((String) any());
        verify(registrationDTO).setId((Long) any());
        verify(registrationDTO).setSecondName((String) any());
        verify(registrationDTO).setUserRoles((java.util.Set<UserRole>) any());
        verify(registrationDTO).setPassword((String) any());
    }

    @Test
    void testUpdateUser() {
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, new Argon2PasswordEncoder());

        UserRepository userRepository1 = mock(UserRepository.class);
        UserController userController = new UserController(userService, userRepository1);

        UserDTO userDTO = new UserDTO();
        userDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setFirstName("Jane");
        userDTO.setId(123L);
        userDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        userDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO.setSecondName("Second Name");
        userDTO.setUserRoles(new HashSet<>());
        assertThrows(SystemException.class, () -> userController.updateUser(1L, userDTO));
    }

    @Test
    void testDeleteUser2() throws Exception {
        doNothing().when(this.userService).delete((Long) any());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteUser3() throws Exception {
        doNothing().when(this.userService).delete((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/users/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(this.userService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllUsers2() throws Exception {
        when(this.userService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/users");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setFirstName("Jane");
        userDTO.setId(123L);
        userDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        userDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO.setSecondName("Second Name");
        userDTO.setUserRoles(new HashSet<>());
        Optional<UserDTO> ofResult = Optional.of(userDTO);
        when(this.userService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1],"
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"email\":\"jane.doe@example.org\",\"firstName\":\"Jane\""
                                        + ",\"secondName\":\"Second Name\",\"userRoles\":[]}"));
    }

    @Test
    void testGetUser2() throws Exception {
        when(this.userService.findOne((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/v1/users/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetUser3() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDTO.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setFirstName("Jane");
        userDTO.setId(123L);
        userDTO.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        userDTO.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO.setSecondName("Second Name");
        userDTO.setUserRoles(new HashSet<>());
        Optional<UserDTO> ofResult = Optional.of(userDTO);
        when(this.userService.findOne((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/users/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"updatedAt\":[1,1,1,1,1],\"updatedBy\":\"Jan 1, 2020 9:00am GMT+0100\",\"createdAt\":[1,1,1,1,1],"
                                        + "\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"id\":123,\"email\":\"jane.doe@example.org\",\"firstName\":\"Jane\""
                                        + ",\"secondName\":\"Second Name\",\"userRoles\":[]}"));
    }
}

