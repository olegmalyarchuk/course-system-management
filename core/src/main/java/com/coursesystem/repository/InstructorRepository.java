package com.coursesystem.repository;

import com.coursesystem.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long>, JpaSpecificationExecutor {

    Optional<Instructor> findByUserId(Long userId);
}
