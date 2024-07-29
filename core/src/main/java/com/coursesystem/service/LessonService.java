package com.coursesystem.service;


import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.dto.CoursePassDTO;
import com.coursesystem.dto.StudentCourseDTO;
import com.coursesystem.dto.LessonDTO;

import java.util.List;
import java.util.Optional;

public interface LessonService {

    LessonDTO save(LessonDTO lessonDTO);

    LessonDTO update(LessonDTO lessonDTO);

    List<LessonDTO> findAll();

    Optional<LessonDTO> findOne(Long id);

    List<LessonDTO> getAllLessonsByStudentId(Long studentId);

    void delete(Long id);

    CoursePassDTO calculateFinalMark(StudentCourseDTO studentCourseDTO);

    LessonDTO findByIdOrThrow(Long id) throws SystemException;
}
