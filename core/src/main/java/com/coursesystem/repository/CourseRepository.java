package com.coursesystem.repository;

import com.coursesystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor {

    List<Course> findAllByIdIn(Collection<Long> courseIds);

    Optional<Course> findByName(String name);
}
