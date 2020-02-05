package com.bytatech.ayoos.patient.service;

import com.bytatech.ayoos.patient.service.dto.DMSRecordDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytatech.ayoos.patient.domain.DMSRecord}.
 */
public interface DMSRecordService {

    /**
     * Save a dMSRecord.
     *
     * @param dMSRecordDTO the entity to save.
     * @return the persisted entity.
     */
    DMSRecordDTO save(DMSRecordDTO dMSRecordDTO);

    /**
     * Get all the dMSRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DMSRecordDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dMSRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DMSRecordDTO> findOne(Long id);

    /**
     * Delete the "id" dMSRecord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the dMSRecord corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DMSRecordDTO> search(String query, Pageable pageable);
}
