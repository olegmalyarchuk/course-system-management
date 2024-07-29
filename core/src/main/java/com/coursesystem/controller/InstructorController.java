package com.coursesystem.controller;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.repository.InstructorRepository;
import com.coursesystem.service.InstructorService;
import com.coursesystem.util.HttpUtil;
import com.coursesystem.util.ResponseUtil;
import com.coursesystem.dto.InstructorDTO;
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
public class InstructorController {

    private final Logger log = LoggerFactory.getLogger(InstructorController.class);

    private static final String ENTITY_NAME = "instructor";

    private final InstructorService instructorService;

    private final InstructorRepository instructorRepository;

    public InstructorController(InstructorService instructorService, InstructorRepository instructorRepository) {
        this.instructorService = instructorService;
        this.instructorRepository = instructorRepository;
    }

    @PostMapping("/instructors")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<InstructorDTO> createInstructor(@Valid @RequestBody InstructorDTO instructorDTO) throws URISyntaxException {
        log.debug("REST request to save Instructor : {}", instructorDTO);
        if (instructorDTO.getId() != null) {
            throw new SystemException("A new instructor cannot already have an ID", ErrorCode.BAD_REQUEST);
        }
        InstructorDTO result = instructorService.save(instructorDTO);
        return ResponseEntity
                .created(new URI("/api/instructors/" + result.getId()))
                .headers(HttpUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/instructors/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<InstructorDTO> updateInstructor(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody InstructorDTO instructorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Instructor : {}, {}", id, instructorDTO);
        if (instructorDTO.getId() == null) {
            throw new SystemException("Invalid id", ErrorCode.BAD_REQUEST);
        }
        if (!Objects.equals(id, instructorDTO.getId())) {
            throw new SystemException("Invalid ID", ErrorCode.BAD_REQUEST);
        }

        if (!instructorRepository.existsById(id)) {
            throw new SystemException("Entity not found", ErrorCode.BAD_REQUEST);
        }
        if (!Objects.equals(instructorRepository.getById(id).getUser().getId(), instructorDTO.getUserId())) {
            throw new SystemException("You are not able to change user reference.", ErrorCode.BAD_REQUEST);
        }

        InstructorDTO result = instructorService.save(instructorDTO);
        return ResponseEntity
                .ok()
                .headers(HttpUtil.createEntityUpdateAlert(ENTITY_NAME, instructorDTO.getId().toString()))
                .body(result);
    }


    @GetMapping("/instructors")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<InstructorDTO> getAllInstructors() {
        log.debug("REST request to get all Instructors");
        return instructorService.findAll();
    }

    @GetMapping("/instructors/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<InstructorDTO> getInstructor(@PathVariable Long id) {
        log.debug("REST request to get Instructor : {}", id);
        Optional<InstructorDTO> instructorDTO = instructorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instructorDTO);
    }

    @DeleteMapping("/instructors/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        log.debug("REST request to delete Instructor : {}", id);
        instructorService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HttpUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
