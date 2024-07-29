package com.coursesystem.unit.service;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.mapper.UserMapper;
import com.coursesystem.model.User;
import com.coursesystem.model.UserRole;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testRegisterUser() {
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
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findOneByEmailIgnoreCase((String) any())).thenReturn(ofResult);

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
        assertThrows(SystemException.class, () -> this.userServiceImpl.registerUser(userDTO, "iloveyou"));
        verify(this.userRepository).findOneByEmailIgnoreCase((String) any());
    }

    @Test
    void testRegisterUser2() {
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
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.findOneByEmailIgnoreCase((String) any())).thenReturn(Optional.empty());

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
        when(this.userMapper.toDto((User) any())).thenReturn(userDTO);
        when(this.userMapper.toEntity((UserDTO) any())).thenReturn(user1);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO1.setEmail("jane.doe@example.org");
        userDTO1.setFirstName("Jane");
        userDTO1.setId(123L);
        userDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        userDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO1.setSecondName("Second Name");
        userDTO1.setUserRoles(new HashSet<>());
        assertSame(userDTO, this.userServiceImpl.registerUser(userDTO1, "iloveyou"));
        verify(this.userRepository).save((User) any());
        verify(this.userRepository).findOneByEmailIgnoreCase((String) any());
        verify(this.userMapper).toEntity((UserDTO) any());
        verify(this.userMapper).toDto((User) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
    }

    @Test
    void testRegisterUser3() {
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
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.findOneByEmailIgnoreCase((String) any())).thenReturn(Optional.empty());

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
        when(this.userMapper.toDto((User) any())).thenReturn(userDTO);
        when(this.userMapper.toEntity((UserDTO) any())).thenReturn(user1);
        when(this.passwordEncoder.encode((CharSequence) any()))
                .thenThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR));

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO1.setEmail("jane.doe@example.org");
        userDTO1.setFirstName("Jane");
        userDTO1.setId(123L);
        userDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        userDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO1.setSecondName("Second Name");
        userDTO1.setUserRoles(new HashSet<>());
        assertThrows(SystemException.class, () -> this.userServiceImpl.registerUser(userDTO1, "iloveyou"));
        verify(this.userRepository).findOneByEmailIgnoreCase((String) any());
        verify(this.userMapper).toEntity((UserDTO) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
    }

    @Test
    void testUpdate() {
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
        Optional<User> ofResult = Optional.of(user);

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
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(this.userMapper.toDto((User) any())).thenReturn(userDTO);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userDTO1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO1.setEmail("jane.doe@example.org");
        userDTO1.setFirstName("Jane");
        userDTO1.setId(123L);
        userDTO1.setUpdatedBy("Jan 1, 2020 9:00am GMT+0100");
        userDTO1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userDTO1.setSecondName("Second Name");
        userDTO1.setUserRoles(new HashSet<>());
        assertSame(userDTO, this.userServiceImpl.update(userDTO1));
        verify(this.userRepository).save((User) any());
        verify(this.userRepository).findById((Long) any());
        verify(this.userMapper).toDto((User) any());
    }

    @Test
    void testUpdate2() {
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
        Optional<User> ofResult = Optional.of(user);

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
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.userMapper.toDto((User) any()))
                .thenThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR));

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
        assertThrows(SystemException.class, () -> this.userServiceImpl.update(userDTO));
        verify(this.userRepository).save((User) any());
        verify(this.userRepository).findById((Long) any());
        verify(this.userMapper).toDto((User) any());
    }

    @Test
    void testFindAll() {
        when(this.userRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.userServiceImpl.findAll().isEmpty());
        verify(this.userRepository).findAll();
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
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(this.userMapper.toDto((User) any())).thenReturn(userDTO);
        assertTrue(this.userServiceImpl.findOne(123L).isPresent());
        verify(this.userRepository).findById((Long) any());
        verify(this.userMapper).toDto((User) any());
    }

    @Test
    void testDelete() {
        doNothing().when(this.userRepository).deleteById((Long) any());
        this.userServiceImpl.delete(123L);
        verify(this.userRepository).deleteById((Long) any());
    }

    @Test
    void testDelete2() {
        doThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR)).when(this.userRepository)
                .deleteById((Long) any());
        assertThrows(SystemException.class, () -> this.userServiceImpl.delete(123L));
        verify(this.userRepository).deleteById((Long) any());
    }

    @Test
    void testFindByEmail() {
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
        when(this.userRepository.findByEmail((String) any())).thenReturn(user);

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
        when(this.userMapper.toDto((User) any())).thenReturn(userDTO);
        assertSame(userDTO, this.userServiceImpl.findByEmail("jane.doe@example.org"));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.userMapper).toDto((User) any());
    }

    @Test
    void testFindByEmailAndPassword() {
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
        when(this.userRepository.findByEmail((String) any())).thenReturn(user);

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
        when(this.userMapper.toDto((User) any())).thenReturn(userDTO);
        when(this.passwordEncoder.matches((CharSequence) any(), (String) any())).thenReturn(true);
        assertSame(userDTO, this.userServiceImpl.findByEmailAndPassword("jane.doe@example.org", "iloveyou"));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.userMapper).toDto((User) any());
        verify(this.passwordEncoder).matches((CharSequence) any(), (String) any());
    }

    @Test
    void testFindByEmailAndPassword2() {
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
        when(this.userRepository.findByEmail((String) any())).thenReturn(user);
        when(this.userMapper.toDto((User) any()))
                .thenThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR));
        when(this.passwordEncoder.matches((CharSequence) any(), (String) any())).thenReturn(true);
        assertThrows(SystemException.class,
                () -> this.userServiceImpl.findByEmailAndPassword("jane.doe@example.org", "iloveyou"));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.userMapper).toDto((User) any());
        verify(this.passwordEncoder).matches((CharSequence) any(), (String) any());
    }

    @Test
    void testFindByEmailAndPassword3() {
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
        when(this.userRepository.findByEmail((String) any())).thenReturn(user);

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
        when(this.userMapper.toDto((User) any())).thenReturn(userDTO);
        when(this.passwordEncoder.matches((CharSequence) any(), (String) any())).thenReturn(false);
        assertThrows(SystemException.class,
                () -> this.userServiceImpl.findByEmailAndPassword("jane.doe@example.org", "iloveyou"));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.passwordEncoder).matches((CharSequence) any(), (String) any());
    }

    @Test
    void testValidateAndGetUser() {
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
        Optional<User> ofResult = Optional.of(user1);

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
        when(this.userRepository.save((User) any())).thenReturn(user2);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.userRepository.getById((Long) any())).thenReturn(user);

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
        when(this.userMapper.toDto((User) any())).thenReturn(userDTO);
        assertSame(userDTO, this.userServiceImpl.validateAndGetUser(123L, UserRole.ADMIN));
        verify(this.userRepository).getById((Long) any());
        verify(this.userRepository).save((User) any());
        verify(this.userRepository).findById((Long) any());
        verify(this.userMapper, atLeast(1)).toDto((User) any());
    }

    @Test
    void testValidateAndGetUser2() {
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
        Optional<User> ofResult = Optional.of(user1);
        when(this.userRepository.save((User) any()))
                .thenThrow(new SystemException("An exception occurred", ErrorCode.INTERNAL_SERVER_ERROR));
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.userRepository.getById((Long) any())).thenReturn(user);

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
        when(this.userMapper.toDto((User) any())).thenReturn(userDTO);
        assertThrows(SystemException.class, () -> this.userServiceImpl.validateAndGetUser(123L, UserRole.ADMIN));
        verify(this.userRepository).getById((Long) any());
        verify(this.userRepository).save((User) any());
        verify(this.userRepository).findById((Long) any());
        verify(this.userMapper).toDto((User) any());
    }
}

