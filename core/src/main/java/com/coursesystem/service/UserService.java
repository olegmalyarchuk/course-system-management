package com.coursesystem.service;

import com.coursesystem.model.User;
import com.coursesystem.model.UserRole;
import com.coursesystem.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Save a user.
     *
     * @param userDTO the entity to save.
     * @return the persisted entity.
     */
    UserDTO update(UserDTO userDTO);

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    List<UserDTO> findAll();

    /**
     * Get the "id" user.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDTO> findOne(Long id);

    /**
     * Delete the "id" user.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    UserDTO findByEmail(String email);

    UserDTO findByEmailAndPassword(String email, String password);

    UserDTO validateAndGetUser(Long userId, UserRole role);

    UserDTO registerUser(UserDTO userDTO, String password);

    void setUserRole(Long userId, String roleName);

    User getUserById(Long userId);
}
