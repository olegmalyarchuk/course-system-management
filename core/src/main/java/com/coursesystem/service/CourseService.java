package com.coursesystem.service;

import com.coursesystem.dto.CourseDTO;
import com.coursesystem.dto.vo.StudentVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface CourseService {
    /**
     * Save a course.
     *
     * @param courseDTO the entity to save.
     * @return the persisted entity.
     */
    CourseDTO save(CourseDTO courseDTO);

    CourseDTO saveOrUpdate(CourseDTO courseDTO);

    /**
     * Get all the courses.
     *
     * @return the list of entities.
     */
    List<CourseDTO> findAll();

    Map<Long, Set<StudentVO>> getAllStudentsPerCourse(Collection<Long> courseIds);

    /**
     * Get the "id" course.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseDTO> findOne(Long id);

    @Transactional(readOnly = true)
    CourseDTO findByIdOrThrow(Long id);

    /**
     * Delete the "id" course.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    CourseDTO assignStudentToCourse(Long courseId, Set<Long> studentIds);

    CourseDTO assignInstructorToCourse(Long courseId, Set<Long> instructorIds);
}
