package com.bytatech.ayoos.patient.service.impl;

import com.bytatech.ayoos.patient.service.DMSRecordService;
import com.bytatech.ayoos.patient.domain.DMSRecord;
import com.bytatech.ayoos.patient.repository.DMSRecordRepository;
import com.bytatech.ayoos.patient.repository.search.DMSRecordSearchRepository;
import com.bytatech.ayoos.patient.service.dto.DMSRecordDTO;
import com.bytatech.ayoos.patient.service.mapper.DMSRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DMSRecord}.
 */
@Service
@Transactional
public class DMSRecordServiceImpl implements DMSRecordService {

    private final Logger log = LoggerFactory.getLogger(DMSRecordServiceImpl.class);

    private final DMSRecordRepository dMSRecordRepository;

    private final DMSRecordMapper dMSRecordMapper;

    private final DMSRecordSearchRepository dMSRecordSearchRepository;

    public DMSRecordServiceImpl(DMSRecordRepository dMSRecordRepository, DMSRecordMapper dMSRecordMapper, DMSRecordSearchRepository dMSRecordSearchRepository) {
        this.dMSRecordRepository = dMSRecordRepository;
        this.dMSRecordMapper = dMSRecordMapper;
        this.dMSRecordSearchRepository = dMSRecordSearchRepository;
    }

    /**
     * Save a dMSRecord.
     *
     * @param dMSRecordDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DMSRecordDTO save(DMSRecordDTO dMSRecordDTO) {
        log.debug("Request to save DMSRecord : {}", dMSRecordDTO);
        DMSRecord dMSRecord = dMSRecordMapper.toEntity(dMSRecordDTO);
        dMSRecord = dMSRecordRepository.save(dMSRecord);
        DMSRecordDTO result = dMSRecordMapper.toDto(dMSRecord);
        dMSRecordSearchRepository.save(dMSRecord);
        return result;
    }

    /**
     * Get all the dMSRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DMSRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DMSRecords");
        return dMSRecordRepository.findAll(pageable)
            .map(dMSRecordMapper::toDto);
    }


    /**
     * Get one dMSRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DMSRecordDTO> findOne(Long id) {
        log.debug("Request to get DMSRecord : {}", id);
        return dMSRecordRepository.findById(id)
            .map(dMSRecordMapper::toDto);
    }

    /**
     * Delete the dMSRecord by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DMSRecord : {}", id);
        dMSRecordRepository.deleteById(id);
        dMSRecordSearchRepository.deleteById(id);
    }

    /**
     * Search for the dMSRecord corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DMSRecordDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DMSRecords for query {}", query);
        return dMSRecordSearchRepository.search(queryStringQuery(query), pageable)
            .map(dMSRecordMapper::toDto);
    }
}
