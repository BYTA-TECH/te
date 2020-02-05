package com.bytatech.ayoos.patient.service.impl;

import com.bytatech.ayoos.patient.service.PatientService;
import com.bytatech.ayoos.patient.domain.Patient;
import com.bytatech.ayoos.patient.repository.PatientRepository;
import com.bytatech.ayoos.patient.repository.search.PatientSearchRepository;
import com.bytatech.ayoos.patient.service.dto.PatientDTO;
import com.bytatech.ayoos.patient.service.mapper.PatientMapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;
import com.bytatech.ayoos.patient.client.dms.model.*;
import com.bytatech.ayoos.patient.client.dms.model.SiteBodyCreate.VisibilityEnum;
import com.bytatech.ayoos.patient.client.dms.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
/**
 * Service Implementation for managing {@link Patient}.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final PatientSearchRepository patientSearchRepository;
@Autowired
    PeopleApi peopleApi;
@Autowired
SitesApi sitesApi;
    
    
    
    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper, PatientSearchRepository patientSearchRepository) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.patientSearchRepository = patientSearchRepository;
    }

    
    
    
    
    /**
     * Save a patient.
     *
     * @param patientDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        log.debug("Request to save Patient : {}", patientDTO);
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        PatientDTO result = patientMapper.toDto(patient);
        patientSearchRepository.save(patient);
        return result;
    }

    /**
     * Get all the patients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PatientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientRepository.findAll(pageable)
            .map(patientMapper::toDto);
    }


    /**
     * Get one patient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id)
            .map(patientMapper::toDto);
    }

    /**
     * Delete the patient by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
        patientSearchRepository.deleteById(id);
    }

    /**
     * Search for the patient corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PatientDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Patients for query {}", query);
        return patientSearchRepository.search(queryStringQuery(query), pageable)
            .map(patientMapper::toDto);
    }
    
    
    
	
	public void createPersonOnDMS( PatientDTO patientDTO) {
		log.debug("=================into the process createPeople()===========");
System.out.println("#################################"+patientDTO.getIdpCode());
		PersonBodyCreate personBodyCreate = new PersonBodyCreate();
		personBodyCreate.setId(patientDTO.getIdpCode());
		personBodyCreate.setFirstName(patientDTO.getIdpCode());
		personBodyCreate.setEmail(patientDTO.getIdpCode()+"@gmail.com");
		personBodyCreate.setPassword(patientDTO.getIdpCode());
		personBodyCreate.setEnabled(true);
		ResponseEntity<PersonEntry> p=peopleApi.createPerson(personBodyCreate, null);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+p.getBody());
	}

    
	public String createSite( String siteId) {
		SiteBodyCreate siteBodyCreate = new SiteBodyCreate();
		siteBodyCreate.setTitle("rafeeksite");
		siteBodyCreate.setId("rafeeksite");
		siteBodyCreate.setVisibility(VisibilityEnum.MODERATED);
		ResponseEntity<SiteEntry> entry = sitesApi.createSite(siteBodyCreate, false, false, new ArrayList());
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@"+entry.getBody().getEntry().getTitle());
		return entry.getBody().getEntry().getId();
	}  
    
    
    
    
    
    
    
    
    
    
    
    
}
