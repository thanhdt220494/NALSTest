package com.nals.test.service;

import com.nals.test.service.dto.WorkDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.nals.test.domain.Work}.
 */
public interface WorkService {

    /**
     * Save a work.
     *
     * @param workDTO the entity to save.
     * @return the persisted entity.
     */
    WorkDTO save(WorkDTO workDTO);

    /**
     * Get all the works.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkDTO> findAll(Pageable pageable);


    /**
     * Get the "id" work.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkDTO> findOne(Long id);

    /**
     * Delete the "id" work.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
