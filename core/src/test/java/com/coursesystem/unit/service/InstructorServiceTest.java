package com.coursesystem.unit.service;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.InstructorDTO;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.mapper.InstructorMapper;
import com.coursesystem.mapper.UserMapper;
import com.coursesystem.model.Instructor;
import com.coursesystem.model.User;
import com.coursesystem.model.UserRole;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.service.UserService;
import com.coursesystem.service.impl.InstructorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {InstructorServiceImpl.class})
@ExtendWith(SpringExtension.class)
class InstructorServiceTest {
    @MockBean
    private InstructorMapper instructorMapper;

    @MockBean
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorServiceImpl instructorServiceImpl;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @Test
    void testSave() {
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
        when(this.userService.validateAndGetUser((Long) any(), (UserRole) any())).thenReturn(userDTO);

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
        when(this.userMapper.toEntity((UserDTO) any())).thenReturn(user);

        User user1 = new User();
        user1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setPassword("iloveyou");
        user1.setSecondName("Second Name");
        user1.setUserRoles("User Roles");

        Instructor instructor = new Instructor();
        instructor.setCourses(new HashSet<>());
        instructor.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructor.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor.setId(123L);
        instructor.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructor.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor.setUser(user1);
        Optional<Instructor> ofResult = Optional.of(instructor);
        when(this.instructorRepository.findByUserId((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user2.setPassword("iloveyou");
        user2.setSecondName("Second Name");
        user2.setUserRoles("User Roles");

        Instructor instructor1 = new Instructor();
        instructor1.setCourses(new HashSet<>());
        instructor1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructor1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor1.setId(123L);
        instructor1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructor1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor1.setUser(user2);
        when(this.instructorMapper.toEntity((InstructorDTO) any())).thenReturn(instructor1);

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
        assertThrows(SystemException.class, () -> this.instructorServiceImpl.save(instructorDTO));
        verify(this.userService).validateAndGetUser((Long) any(), (UserRole) any());
        verify(this.instructorRepository).findByUserId((Long) any());
        verify(this.instructorMapper).toEntity((InstructorDTO) any());
    }

    @Test
    void testSave2() {
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
        when(this.userService.validateAndGetUser((Long) any(), (UserRole) any())).thenReturn(userDTO);

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
        when(this.userMapper.toEntity((UserDTO) any())).thenReturn(user);

        User user1 = new User();
        user1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        user1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setPassword("iloveyou");
        user1.setSecondName("Second Name");
        user1.setUserRoles("User Roles");

        Instructor instructor = new Instructor();
        instructor.setCourses(new HashSet<>());
        instructor.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructor.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor.setId(123L);
        instructor.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructor.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor.setUser(user1);
        Optional<Instructor> ofResult = Optional.of(instructor);
        when(this.instructorRepository.findByUserId((Long) any())).thenReturn(ofResult);
        when(this.instructorMapper.toEntity((InstructorDTO) any()))
                .thenThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR));

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
        assertThrows(SystemException.class, () -> this.instructorServiceImpl.save(instructorDTO));
        verify(this.instructorMapper).toEntity((InstructorDTO) any());
    }

    @Test
    void testFindAll() {
        when(this.instructorRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.instructorServiceImpl.findAll().isEmpty());
        verify(this.instructorRepository).findAll();
    }

    @Test
    void testFindOne() {
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

        Instructor instructor = new Instructor();
        instructor.setCourses(new HashSet<>());
        instructor.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        instructor.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor.setId(123L);
        instructor.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        instructor.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        instructor.setUser(user);
        Optional<Instructor> ofResult = Optional.of(instructor);
        when(this.instructorRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(this.instructorMapper.toDto((Instructor) any())).thenReturn(instructorDTO);
        assertTrue(this.instructorServiceImpl.findOne(123L).isPresent());
        verify(this.instructorRepository).findById((Long) any());
        verify(this.instructorMapper).toDto((Instructor) any());
    }

    @Test
    void testDelete() {
        doNothing().when(this.instructorRepository).deleteById((Long) any());
        this.instructorServiceImpl.delete(123L);
        verify(this.instructorRepository).deleteById((Long) any());
    }

    @Test
    void testDelete2() {
        doThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR)).when(this.instructorRepository)
                .deleteById((Long) any());
        assertThrows(SystemException.class, () -> this.instructorServiceImpl.delete(123L));
        verify(this.instructorRepository).deleteById((Long) any());
    }
}

