package com.coursesystem.unit.service;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.StudentDTO;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.mapper.StudentMapper;
import com.coursesystem.mapper.UserMapper;
import com.coursesystem.model.Student;
import com.coursesystem.model.User;
import com.coursesystem.model.UserRole;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.service.UserService;
import com.coursesystem.service.impl.StudentServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {StudentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @MockBean
    private StudentMapper studentMapper;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentServiceImpl studentServiceImpl;

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

        Student student = new Student();
        student.setCourses(new HashSet<>());
        student.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        student.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setId(123L);
        student.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        student.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setUser(user1);
        Optional<Student> ofResult = Optional.of(student);
        when(this.studentRepository.findByUserId((Long) any())).thenReturn(ofResult);

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

        Student student1 = new Student();
        student1.setCourses(new HashSet<>());
        student1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        student1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setId(123L);
        student1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        student1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setUser(user2);
        when(this.studentMapper.toEntity((StudentDTO) any())).thenReturn(student1);

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
        assertThrows(SystemException.class, () -> this.studentServiceImpl.save(studentDTO));
        verify(this.userService).validateAndGetUser((Long) any(), (UserRole) any());
        verify(this.studentRepository).findByUserId((Long) any());
        verify(this.studentMapper).toEntity((StudentDTO) any());
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

        Student student = new Student();
        student.setCourses(new HashSet<>());
        student.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        student.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setId(123L);
        student.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        student.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setUser(user1);
        Optional<Student> ofResult = Optional.of(student);
        when(this.studentRepository.findByUserId((Long) any())).thenReturn(ofResult);
        when(this.studentMapper.toEntity((StudentDTO) any()))
                .thenThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR));

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
        assertThrows(SystemException.class, () -> this.studentServiceImpl.save(studentDTO));
        verify(this.studentMapper).toEntity((StudentDTO) any());
    }

    @Test
    void testFindAll() {
        when(this.studentRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.studentServiceImpl.findAll().isEmpty());
        verify(this.studentRepository).findAll();
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

        Student student = new Student();
        student.setCourses(new HashSet<>());
        student.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        student.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setId(123L);
        student.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        student.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setUser(user);
        Optional<Student> ofResult = Optional.of(student);
        when(this.studentRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(this.studentMapper.toDto((Student) any())).thenReturn(studentDTO);
        assertTrue(this.studentServiceImpl.findOne(123L).isPresent());
        verify(this.studentRepository).findById((Long) any());
        verify(this.studentMapper).toDto((Student) any());
    }

    @Test
    void testDelete() {
        doNothing().when(this.studentRepository).deleteById((Long) any());
        this.studentServiceImpl.delete(123L);
        verify(this.studentRepository).deleteById((Long) any());
    }

    @Test
    void testDelete2() {
        doThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR)).when(this.studentRepository)
                .deleteById((Long) any());
        assertThrows(SystemException.class, () -> this.studentServiceImpl.delete(123L));
        verify(this.studentRepository).deleteById((Long) any());
    }

    @Test
    void testGetStudentOrThrow() {
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

        Student student = new Student();
        student.setCourses(new HashSet<>());
        student.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        student.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setId(123L);
        student.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        student.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setUser(user);
        Optional<Student> ofResult = Optional.of(student);
        when(this.studentRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(this.studentMapper.toDto((Student) any())).thenReturn(studentDTO);
        assertSame(studentDTO, this.studentServiceImpl.getStudentOrThrow(123L));
        verify(this.studentRepository).findById((Long) any());
        verify(this.studentMapper).toDto((Student) any());
    }

    @Test
    void testGetStudentOrThrow3() {
        when(this.studentRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        when(this.studentMapper.toDto((Student) any())).thenReturn(studentDTO);
        assertThrows(SystemException.class, () -> this.studentServiceImpl.getStudentOrThrow(123L));
        verify(this.studentRepository).findById((Long) any());
    }
}

