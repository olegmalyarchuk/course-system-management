package com.coursesystem.repository;

import com.coursesystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    User findByEmail(String email);

    Optional<User> findOneByEmailIgnoreCase(String email);

    @Modifying
    @Query(value = "update promotion.user set user_roles = :role where id = :id", nativeQuery = true)
    void updateUserRole(Long id, String role);
}
