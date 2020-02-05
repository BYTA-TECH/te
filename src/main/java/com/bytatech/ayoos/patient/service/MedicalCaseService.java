package com.bytatech.ayoos.patient.service;

import com.bytatech.ayoos.patient.service.dto.MedicalCaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytatech.ayoos.patient.domain.MedicalCase}.
 */
public interface MedicalCaseService {

    /**
     * Save a medicalCase.
     *
     * @param medicalCaseDTO the entity to save.
     * @return the persisted entity.
     */
    MedicalCaseDTO save(MedicalCaseDTO medicalCaseDTO);

    /**
     * Get all the medicalCases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalCaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" medicalCase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalCaseDTO> findOne(Long id);

    /**
     * Delete the "id" medicalCase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the medicalCase corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalCaseDTO> search(String query, Pageable pageable);
}
