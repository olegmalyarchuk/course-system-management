package com.coursesystem.service.impl;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.mapper.StudentMapper;
import com.coursesystem.model.Student;
import com.coursesystem.model.UserRole;
import com.coursesystem.dto.StudentDTO;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.service.StudentService;
import com.coursesystem.service.UserService;
import com.coursesystem.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public StudentServiceImpl(final StudentRepository studentRepository, final StudentMapper studentMapper,
                              final UserService userService, final UserMapper userMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        final UserDTO userDTO = userService.validateAndGetUser(studentDTO.getUserId(), UserRole.STUDENT);
        if (studentRepository.findByUserId(userDTO.getId()).isPresent()) {
            throw new SystemException(String.format("You are not able to create a new student with userId: %d and email: %s", userDTO.getId(), userDTO.getEmail()), ErrorCode.BAD_REQUEST);
        }
        student.setUser(userMapper.toEntity(userDTO));
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> findAll() {
        log.debug("Request to get all Students");
        return studentRepository.findAll().stream().map(studentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id).map(studentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDTO getStudentOrThrow(final Long studentId) {
        log.debug("Request to get Student : {}", studentId);
        return studentRepository.findById(studentId)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new SystemException(String.format("Student with id: %d doesn't exist.", studentId), ErrorCode.BAD_REQUEST));
    }
}

