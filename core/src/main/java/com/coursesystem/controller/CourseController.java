package com.coursesystem.controller;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.CourseDTO;
import com.coursesystem.dto.vo.StudentVO;
import com.coursesystem.repository.CourseRepository;
import com.coursesystem.service.CourseService;
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
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class CourseController {

    private final Logger log = LoggerFactory.getLogger(CourseController.class);

    private static final String ENTITY_NAME = "course";

    private final CourseService courseService;

    private final CourseRepository courseRepository;

    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/courses")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) throws URISyntaxException, SystemException {
        log.debug("REST request to save Course : {}", courseDTO);
        if (courseDTO.getId() != null) {
            throw new SystemException("A new course cannot already have an ID", ErrorCode.BAD_REQUEST);
        }
        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity
                .created(new URI("/api/courses/" + result.getId()))
                .headers(HttpUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PostMapping("/course/{courseId}/add-student")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<CourseDTO> addStudentToCourse(@PathVariable final Long courseId, @RequestParam final Set<Long> studentIds) throws URISyntaxException, SystemException {
        log.debug("REST request to add Student(s) with id: {}, to course: {}", studentIds, courseId);
        CourseDTO result = courseService.assignStudentToCourse(courseId, studentIds);
        return ResponseEntity
                .created(new URI("/api/courses/" + result.getId()))
                .headers(HttpUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PostMapping("/course/{courseId}/add-instructor")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CourseDTO> addInstructorToCourse(@PathVariable final Long courseId, @RequestParam final Set<Long> instructorIds) throws URISyntaxException, SystemException {
        log.debug("REST request to add Instructor(s) with id: {}, to course: {}", instructorIds, courseId);
        CourseDTO result = courseService.assignInstructorToCourse(courseId, instructorIds);
        return ResponseEntity
                .created(new URI("/api/courses/" + result.getId()))
                .headers(HttpUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/courses/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<CourseDTO> updateCourse(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody CourseDTO courseDTO
    ) {
        log.debug("REST request to update Course : {}, {}", id, courseDTO);
        if (courseDTO.getId() == null) {
            throw new SystemException("Invalid id", ErrorCode.BAD_REQUEST);
        }
        if (!Objects.equals(id, courseDTO.getId())) {
            throw new SystemException("Invalid ID", ErrorCode.BAD_REQUEST);
        }

        if (!courseRepository.existsById(id)) {
            throw new SystemException("Entity not found", ErrorCode.BAD_REQUEST);
        }

        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity
                .ok()
                .headers(HttpUtil.createEntityUpdateAlert(ENTITY_NAME, courseDTO.getId().toString()))
                .body(result);
    }


    @GetMapping("/courses")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CourseDTO> getAllCourses() {
        log.debug("REST request to get all Courses");
        return courseService.findAll();
    }

    @GetMapping("/courses/students")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public Map<Long, Set<StudentVO>> getAllStudentsPerCourse(@RequestParam Collection<Long> courseIds) {
        log.debug("REST request to get Courses by ids: {}", courseIds);
        return courseService.getAllStudentsPerCourse(courseIds);
    }

    @GetMapping("/courses/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        Optional<CourseDTO> courseDTO = courseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseDTO);
    }

    @DeleteMapping("/courses/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        courseService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HttpUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
