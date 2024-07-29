package com.coursesystem.service;

import com.coursesystem.dto.CourseReviewDTO;

import java.util.List;
import java.util.Optional;

public interface CourseReviewService {

    CourseReviewDTO save(CourseReviewDTO courseReviewDTO);

    List<CourseReviewDTO> findAll();

    Optional<CourseReviewDTO> findOne(Long id);

    void delete(Long id);

}
