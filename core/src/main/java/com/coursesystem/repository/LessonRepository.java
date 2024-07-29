package com.coursesystem.repository;

import com.coursesystem.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>, JpaSpecificationExecutor {

    List<Lesson> findByStudentId(Long studentId);

    List<Lesson> findByStudentIdAndCourseId(Long studentId, Long courseId);

    Optional<Lesson> findByStudentIdAndCourseIdAndLessonNumber(Long studentId, Long courseId, Short lessonNumber);
}
