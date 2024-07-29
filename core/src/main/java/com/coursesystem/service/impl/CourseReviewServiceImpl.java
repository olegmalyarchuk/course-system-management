package com.coursesystem.service.impl;

import com.coursesystem.dto.CourseReviewDTO;
import com.coursesystem.mapper.CourseReviewMapper;
import com.coursesystem.model.CourseReview;
import com.coursesystem.repository.CourseReviewRepository;
import com.coursesystem.service.CourseReviewService;
import com.coursesystem.service.CourseService;
import com.coursesystem.service.StudentService;
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
public class CourseReviewServiceImpl implements CourseReviewService {

    private final Logger log = LoggerFactory.getLogger(CourseReviewServiceImpl.class);

    private final CourseReviewRepository courseReviewRepository;
    private final CourseReviewMapper courseReviewMapper;
    private final CourseService courseService;
    private final StudentService studentService;

    public CourseReviewServiceImpl(final CourseReviewRepository courseReviewRepository,
                                   final CourseReviewMapper courseReviewMapper,
                                   final CourseService courseService,
                                   final StudentService studentService) {
        this.courseReviewRepository = courseReviewRepository;
        this.courseReviewMapper = courseReviewMapper;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Override
    public CourseReviewDTO save(final CourseReviewDTO courseReviewDTO) {
        log.debug("Request to save CourseReview : {}", courseReviewDTO);
        verifyCourseReview(courseReviewDTO);
        CourseReview courseReview = courseReviewMapper.toEntity(courseReviewDTO);
        courseReview = courseReviewRepository.save(courseReview);
        return courseReviewMapper.toDto(courseReview);
    }

    private void verifyCourseReview(final CourseReviewDTO courseReviewDTO) {
        courseService.findByIdOrThrow(courseReviewDTO.getCourseId());
        studentService.getStudentOrThrow(courseReviewDTO.getStudentId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseReviewDTO> findAll() {
        log.debug("Request to get all CourseReviews");
        return courseReviewRepository.findAll().stream().map(courseReviewMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseReviewDTO> findOne(final Long id) {
        log.debug("Request to get CourseReview : {}", id);
        return courseReviewRepository.findById(id).map(courseReviewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseReview : {}", id);
        courseReviewRepository.deleteById(id);
    }

}
