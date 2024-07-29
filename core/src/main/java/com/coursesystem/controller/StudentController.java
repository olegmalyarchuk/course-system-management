package com.coursesystem.controller;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.StudentDTO;
import com.coursesystem.repository.StudentRepository;
import com.coursesystem.service.StudentService;
import com.coursesystem.util.HttpUtil;
import com.coursesystem.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final Logger log = LoggerFactory.getLogger(StudentController.class);

    private static final String ENTITY_NAME = "student";

    private final StudentService studentService;

    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/students")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) throws URISyntaxException {
        log.debug("REST request to save Student : {}", studentDTO);
        if (studentDTO.getId() != null) {
            throw new SystemException("A new student cannot already have an ID", ErrorCode.BAD_REQUEST);
        }
        StudentDTO result = studentService.save(studentDTO);
        return ResponseEntity
            .created(new URI("/api/students/" + result.getId()))
            .headers(HttpUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/students/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    public ResponseEntity<StudentDTO> updateStudent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StudentDTO studentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Student : {}, {}", id, studentDTO);
        if (studentDTO.getId() == null) {
            throw new SystemException("Invalid id", ErrorCode.BAD_REQUEST);
        }
        if (!Objects.equals(id, studentDTO.getId())) {
            throw new SystemException("Invalid ID", ErrorCode.BAD_REQUEST);
        }

        if (!studentRepository.existsById(id)) {
            throw new SystemException("Entity not found", ErrorCode.BAD_REQUEST);
        }
        if (!Objects.equals(studentRepository.getById(id).getUser().getId(), studentDTO.getUserId())) {
            throw new SystemException("You are not able to change user reference.", ErrorCode.BAD_REQUEST);
        }

        StudentDTO result = studentService.save(studentDTO);
        return ResponseEntity
                .ok()
                .headers(HttpUtil.createEntityUpdateAlert(ENTITY_NAME, studentDTO.getId().toString()))
                .body(result);
    }

    @GetMapping("/students")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public List<StudentDTO> getAllStudents() {
        log.debug("REST request to get all Students");
        return studentService.findAll();
    }

    @GetMapping("/students/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        log.debug("REST request to get Student : {}", id);
        Optional<StudentDTO> studentDTO = studentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentDTO);
    }

    @DeleteMapping("/students/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.debug("REST request to delete Student : {}", id);
        studentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HttpUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .build();
    }
}
