package com.nals.test.service.impl;

import com.nals.test.service.WorkService;
import com.nals.test.domain.Work;
import com.nals.test.repository.WorkRepository;
import com.nals.test.service.dto.WorkDTO;
import com.nals.test.service.mapper.WorkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Work}.
 */
@Service
@Transactional
public class WorkServiceImpl implements WorkService {

    private final Logger log = LoggerFactory.getLogger(WorkServiceImpl.class);

    private final WorkRepository workRepository;

    private final WorkMapper workMapper;

    public WorkServiceImpl(WorkRepository workRepository, WorkMapper workMapper) {
        this.workRepository = workRepository;
        this.workMapper = workMapper;
    }

    /**
     * Save a work.
     *
     * @param workDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkDTO save(WorkDTO workDTO) {
        log.debug("Request to save Work : {}", workDTO);
        Work work = workMapper.toEntity(workDTO);
        work = workRepository.save(work);
        return workMapper.toDto(work);
    }

    /**
     * Get all the works.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Works");
        return workRepository.findAll(pageable)
            .map(workMapper::toDto);
    }


    /**
     * Get one work by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkDTO> findOne(Long id) {
        log.debug("Request to get Work : {}", id);
        return workRepository.findById(id)
            .map(workMapper::toDto);
    }

    /**
     * Delete the work by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Work : {}", id);
        workRepository.deleteById(id);
    }
}
