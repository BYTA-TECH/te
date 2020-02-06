package com.bytatech.ayoos.patient.service;

import com.bytatech.ayoos.patient.client.dms.model.SiteMemberEntry;
import com.bytatech.ayoos.patient.service.dto.PatientDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytatech.ayoos.patient.domain.Patient}.
 */
public interface PatientService {

    /**
     * Save a patient.
     *
     * @param patientDTO the entity to save.
     * @return the persisted entity.
     */
    PatientDTO save(PatientDTO patientDTO);

    /**
     * Get all the patients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatientDTO> findAll(Pageable pageable);


    /**
     * Get the "id" patient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientDTO> findOne(Long id);

    /**
     * Delete the "id" patient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the patient corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatientDTO> search(String query, Pageable pageable);
    
    public void createPersonOnDMS( PatientDTO patientDTO);
    
	public String createSite( String siteId) ;
	public SiteMemberEntry createSiteMembership(String siteId, String id);
    
    
}
