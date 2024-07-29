package com.coursesystem.controller;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.coursesystem.controller.dto.RegistrationDTO;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.util.HttpUtil;
import com.coursesystem.dto.UserDTO;
import com.coursesystem.service.UserService;
import com.coursesystem.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String ENTITY_NAME = "user";

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(final UserService userService,
                          final UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerAccount(@Valid @RequestBody RegistrationDTO registrationDTO) {
        log.debug("REST request to register User : {}", registrationDTO);
        if (registrationDTO.getId() != null) {
            throw new SystemException("A new user cannot already have an ID", ErrorCode.BAD_REQUEST);
        }
        UserDTO result = userService.registerUser(registrationDTO, registrationDTO.getPassword());
        return ResponseEntity
                .ok()
                .headers(HttpUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/users/{userId}/role")
    public ResponseEntity<Void> assignRoleForUser(@PathVariable Long userId, @RequestBody String role) {
        userService.setUserRole(userId, role);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody UserDTO userDTO
    ) {
        log.debug("REST request to update User : {}, {}", id, userDTO);
        if (userDTO.getId() == null) {
            throw new SystemException("Invalid id", ErrorCode.BAD_REQUEST);
        }
        if (!Objects.equals(id, userDTO.getId())) {
            throw new SystemException("Invalid ID", ErrorCode.BAD_REQUEST);
        }

        if (!userRepository.existsById(id)) {
            throw new SystemException("Entity not found", ErrorCode.BAD_REQUEST);
        }

        UserDTO result = userService.update(userDTO);
        return ResponseEntity
                .ok()
                .headers(HttpUtil.createEntityUpdateAlert(ENTITY_NAME, userDTO.getId().toString()))
                .body(result);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDTO> getAllUsers() {
        log.debug("REST request to get all Users");
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        Optional<UserDTO> userDTO = userService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDTO);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        userService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HttpUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
