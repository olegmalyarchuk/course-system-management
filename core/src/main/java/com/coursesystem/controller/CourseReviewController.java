package com.coursesystem.controller;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.CourseReviewDTO;
import com.coursesystem.repository.CourseReviewRepository;
import com.coursesystem.service.CourseReviewService;
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
public class CourseReviewController {

    private final Logger log = LoggerFactory.getLogger(CourseReviewController.class);

    private static final String ENTITY_NAME = "course-review";

    private final CourseReviewService courseReviewService;
    private final CourseReviewRepository courseReviewRepository;

    public CourseReviewController(final CourseReviewService courseReviewService,
                                  final CourseReviewRepository courseReviewRepository) {
        this.courseReviewService = courseReviewService;
        this.courseReviewRepository = courseReviewRepository;
    }

    @PostMapping("/course-review")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<CourseReviewDTO> createCourseReview(@Valid @RequestBody CourseReviewDTO courseReviewDTO) throws URISyntaxException, SystemException {
        log.debug("REST request to save CourseReview : {}", courseReviewDTO);
        if (courseReviewDTO.getId() != null) {
            throw new SystemException("A new courseReview cannot already have an ID", ErrorCode.BAD_REQUEST);
        }
        CourseReviewDTO result = courseReviewService.save(courseReviewDTO);
        return ResponseEntity
                .created(new URI("/api/course-review/" + result.getId()))
                .headers(HttpUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/course-review/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<CourseReviewDTO> updateCourseReview(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody CourseReviewDTO courseReviewDTO) {
        log.debug("REST request to update CourseReview : {}, {}", id, courseReviewDTO);
        if (courseReviewDTO.getId() == null) {
            throw new SystemException("Invalid id", ErrorCode.BAD_REQUEST);
        }
        if (!Objects.equals(id, courseReviewDTO.getId())) {
            throw new SystemException("Invalid ID", ErrorCode.BAD_REQUEST);
        }

        if (!courseReviewRepository.existsById(id)) {
            throw new SystemException("Entity not found", ErrorCode.BAD_REQUEST);
        }

        CourseReviewDTO result = courseReviewService.save(courseReviewDTO);
        return ResponseEntity.ok()
                .headers(HttpUtil.createEntityUpdateAlert(ENTITY_NAME, courseReviewDTO.getId().toString()))
                .body(result);
    }

    @GetMapping("/course-reviews")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<CourseReviewDTO> getAllCourseReviews() {
        log.debug("REST request to get all Courses Reviews");
        return courseReviewService.findAll();
    }

    @GetMapping("/course-review/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<CourseReviewDTO> getCourseReview(@PathVariable Long id) {
        log.debug("REST request to get CourseReview : {}", id);
        Optional<CourseReviewDTO> courseReviewDTO = courseReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseReviewDTO);
    }

    @DeleteMapping("/course-review/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<Void> deleteCourseReview(@PathVariable Long id) {
        log.debug("REST request to delete CourseReview : {}", id);
        courseReviewService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HttpUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
