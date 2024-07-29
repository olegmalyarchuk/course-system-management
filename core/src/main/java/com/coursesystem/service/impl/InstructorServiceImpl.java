package com.coursesystem.service.impl;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.mapper.InstructorMapper;
import com.coursesystem.service.InstructorService;
import com.coursesystem.model.Instructor;
import com.coursesystem.model.UserRole;
import com.coursesystem.dto.InstructorDTO;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.repository.InstructorRepository;
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
public class InstructorServiceImpl implements InstructorService {

    private final Logger log = LoggerFactory.getLogger(InstructorServiceImpl.class);

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public InstructorServiceImpl(final InstructorRepository instructorRepository, final InstructorMapper instructorMapper,
                                 final UserService userService, final UserMapper userMapper) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public InstructorDTO save(InstructorDTO instructorDTO) {
        log.debug("Request to save Instructor : {}", instructorDTO);
        Instructor instructor = instructorMapper.toEntity(instructorDTO);
        final UserDTO userDTO = userService.validateAndGetUser(instructorDTO.getUserId(), UserRole.INSTRUCTOR);
        if (instructorRepository.findByUserId(userDTO.getId()).isPresent()) {
            throw new SystemException(String.format("You are not able to create a new instructor with userid: %d and email: %s", userDTO.getId(), userDTO.getEmail()), ErrorCode.BAD_REQUEST);
        }
        instructor.setUser(userMapper.toEntity(userDTO));
        instructor = instructorRepository.save(instructor);
        return instructorMapper.toDto(instructor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstructorDTO> findAll() {
        log.debug("Request to get all Students");
        return instructorRepository.findAll().stream().map(instructorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InstructorDTO> findOne(Long id) {
        log.debug("Request to get Instructor : {}", id);
        return instructorRepository.findById(id).map(instructorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Instructor : {}", id);
        instructorRepository.deleteById(id);
    }
}
