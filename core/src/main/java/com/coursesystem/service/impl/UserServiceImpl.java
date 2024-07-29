package com.coursesystem.service.impl;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.mapper.UserMapper;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.service.UserService;
import com.coursesystem.util.JsonUtil;
import com.coursesystem.model.User;
import com.coursesystem.model.UserRole;
import com.coursesystem.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(final UserRepository userRepository, final UserMapper userMapper,
                           final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerUser(final UserDTO userDTO, final String password) {
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail())
                .ifPresent(existingUser -> {
                    throw new SystemException(String.format("User with email: %s already exist.", userDTO.getEmail()), ErrorCode.BAD_REQUEST);
                });
        log.debug("Request to save User : {}", userDTO);
        validateRole(userDTO);
        User newUser = userMapper.toEntity(userDTO);
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encryptedPassword);
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        final User savedUser = userRepository.save(newUser);
        log.debug("Created Information for User: {}", savedUser);
        return userMapper.toDto(savedUser);
    }

    private void validateRole(final UserDTO userDTO) {
        if (userDTO.getUserRoles().contains(UserRole.ADMIN)) {
            throw new SystemException(String.format("You are not able to create user with role: %s.", UserRole.ADMIN), ErrorCode.BAD_REQUEST);
        }
    }

    @Override
    public UserDTO update(final UserDTO userDTO) {
        final User user = userRepository.findById(userDTO.getId()).get();
        user.setFirstName(userDTO.getFirstName());
        user.setSecondName(userDTO.getSecondName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setUserRoles(JsonUtil.serialize(userDTO.getUserRoles()));
        userRepository.save(user);
        log.debug("Changed Information for User: {}", user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        log.debug("Request to get all Students");
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findOne(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findByEmail(final String email) {
        return Optional.ofNullable(userRepository.findByEmail(email.toLowerCase()))
                .map(userMapper::toDto)
                .orElseThrow(() -> new SystemException(String.format("System not able to find user with email: \"%s\"", email), ErrorCode.BAD_REQUEST));
    }

    @Override
    public UserDTO findByEmailAndPassword(final String email, final String password) {
        final Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new SystemException("Wrong email or password.", ErrorCode.BAD_REQUEST);
        }
        return userMapper.toDto(user.get());
    }

    @Override
    public UserDTO validateAndGetUser(final Long userId, final UserRole role) {
        final User user = userRepository.getById(userId);
        if (!user.getUserRoles().contains(role.name())) {
            final UserDTO userDTO = userMapper.toDto(user);
            userDTO.getUserRoles().add(role);
            return this.update(userDTO);
        }
        return userMapper.toDto(user);
    }

    @Override
    public void setUserRole(Long userId, String userRole) {
        User user = getUserById(userId);

        if (user.getUserRoles().contains(userRole)) {
            throw new SystemException("User with id: " + userId + " already has assigned role " + userRole, ErrorCode.BAD_REQUEST);
        }

        if (isNull(UserRole.fromValue(userRole))) {
            throw new SystemException("Role with name " + userRole + " doesn't exist, available values " + Arrays.toString(UserRole.values()), ErrorCode.BAD_REQUEST);
        }

        userRepository.updateUserRole(user.getId(), userRole);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new SystemException("User with id: " + userId + " not found", ErrorCode.BAD_REQUEST));
    }
}
