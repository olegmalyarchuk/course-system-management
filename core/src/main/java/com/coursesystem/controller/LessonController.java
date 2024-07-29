package com.coursesystem.controller;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.CoursePassDTO;
import com.coursesystem.dto.StudentCourseDTO;
import com.coursesystem.repository.LessonRepository;
import com.coursesystem.service.LessonService;
import com.coursesystem.util.HttpUtil;
import com.coursesystem.util.ResponseUtil;
import com.coursesystem.dto.LessonDTO;
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
public class LessonController {

    private final Logger log = LoggerFactory.getLogger(LessonController.class);

    private static final String ENTITY_NAME = "lesson";

    private final LessonService lessonService;

    private final LessonRepository lessonRepository;

    public LessonController(LessonService lessonService, LessonRepository lessonRepository) {
        this.lessonService = lessonService;
        this.lessonRepository = lessonRepository;
    }

    @PostMapping("/lessons")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<LessonDTO> createLesson(@Valid @RequestBody LessonDTO lessonDTO) throws URISyntaxException {
        log.debug("REST request to save Lesson : {}", lessonDTO);
        if (lessonDTO.getId() != null) {
            throw new SystemException("A new lesson cannot already have an ID", ErrorCode.BAD_REQUEST);
        }
        LessonDTO result = lessonService.save(lessonDTO);
        return ResponseEntity
                .created(new URI("/api/lessons/" + result.getId()))
                .headers(HttpUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/lessons/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<LessonDTO> updateLesson(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody LessonDTO lessonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Lesson : {}, {}", id, lessonDTO);
        if (lessonDTO.getId() == null) {
            throw new SystemException("Invalid id", ErrorCode.BAD_REQUEST);
        }
        if (!Objects.equals(id, lessonDTO.getId())) {
            throw new SystemException("Invalid ID", ErrorCode.BAD_REQUEST);
        }

        if (!lessonRepository.existsById(id)) {
            throw new SystemException("Entity not found", ErrorCode.BAD_REQUEST);
        }

        LessonDTO result = lessonService.update(lessonDTO);
        return ResponseEntity
                .ok()
                .headers(HttpUtil.createEntityUpdateAlert(ENTITY_NAME, lessonDTO.getId().toString()))
                .body(result);
    }

    @GetMapping("/lessons")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<LessonDTO> getAllLessons() {
        log.debug("REST request to get all Lessons");
        return lessonService.findAll();
    }

    @GetMapping("/lessons/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<LessonDTO> getLesson(@PathVariable Long id) {
        log.debug("REST request to get Lesson : {}", id);
        Optional<LessonDTO> lessonDTO = lessonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lessonDTO);
    }

    @GetMapping("/lessons/student/{studentId}")
    @PreAuthorize("isAuthenticated()")
    public List<LessonDTO> getAllLessonsForStudent(@PathVariable Long studentId) {
        List<LessonDTO> lessonDTOs = lessonService.getAllLessonsByStudentId(studentId);
        return lessonDTOs;
    }

    @PostMapping("/lessons/final-mark")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<CoursePassDTO> getFinalMarkForLesson(@RequestBody StudentCourseDTO studentCourseDTO) {
        log.debug("REST request to calculate final mark for course by student : {}", studentCourseDTO);
        final CoursePassDTO finalMark = lessonService.calculateFinalMark(studentCourseDTO);
        return ResponseEntity.ok(finalMark);
    }

    @DeleteMapping("/lessons/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        log.debug("REST request to delete Lesson : {}", id);
        lessonService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HttpUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
