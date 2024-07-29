package com.coursesystem.service;


import com.coursesystem.dto.InstructorDTO;

import java.util.List;
import java.util.Optional;

public interface InstructorService {
    /**
     * Save an instructor.
     *
     * @param instructorDTO the entity to save.
     * @return the persisted entity.
     */
    InstructorDTO save(InstructorDTO instructorDTO);

    /**
     * Get all the instructors.
     *
     * @return the list of entities.
     */
    List<InstructorDTO> findAll();

    /**
     * Get the "id" instructor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InstructorDTO> findOne(Long id);

    /**
     * Delete the "id" instructor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
