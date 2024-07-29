package com.coursesystem.repository;

import com.coursesystem.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long>, JpaSpecificationExecutor {

    Optional<Homework> findByLessonId(Long lessonId);
}
