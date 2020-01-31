package com.bytatech.ayoos.patient.web.rest;

import com.bytatech.ayoos.patient.PatientApp;
import com.bytatech.ayoos.patient.config.TestSecurityConfiguration;
import com.bytatech.ayoos.patient.domain.DMSRecord;
import com.bytatech.ayoos.patient.repository.DMSRecordRepository;
import com.bytatech.ayoos.patient.repository.search.DMSRecordSearchRepository;
import com.bytatech.ayoos.patient.service.DMSRecordService;
import com.bytatech.ayoos.patient.service.dto.DMSRecordDTO;
import com.bytatech.ayoos.patient.service.mapper.DMSRecordMapper;
import com.bytatech.ayoos.patient.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.bytatech.ayoos.patient.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DMSRecordResource} REST controller.
 */
@SpringBootTest(classes = {PatientApp.class, TestSecurityConfiguration.class})
public class DMSRecordResourceIT {

    private static final String DEFAULT_PRESCRIPTION_REF = "AAAAAAAAAA";
    private static final String UPDATED_PRESCRIPTION_REF = "BBBBBBBBBB";

    @Autowired
    private DMSRecordRepository dMSRecordRepository;

    @Autowired
    private DMSRecordMapper dMSRecordMapper;

    @Autowired
    private DMSRecordService dMSRecordService;

    /**
     * This repository is mocked in the com.bytatech.ayoos.patient.repository.search test package.
     *
     * @see com.bytatech.ayoos.patient.repository.search.DMSRecordSearchRepositoryMockConfiguration
     */
    @Autowired
    private DMSRecordSearchRepository mockDMSRecordSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDMSRecordMockMvc;

    private DMSRecord dMSRecord;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DMSRecordResource dMSRecordResource = new DMSRecordResource(dMSRecordService);
        this.restDMSRecordMockMvc = MockMvcBuilders.standaloneSetup(dMSRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DMSRecord createEntity(EntityManager em) {
        DMSRecord dMSRecord = new DMSRecord()
            .prescriptionRef(DEFAULT_PRESCRIPTION_REF);
        return dMSRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DMSRecord createUpdatedEntity(EntityManager em) {
        DMSRecord dMSRecord = new DMSRecord()
            .prescriptionRef(UPDATED_PRESCRIPTION_REF);
        return dMSRecord;
    }

    @BeforeEach
    public void initTest() {
        dMSRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createDMSRecord() throws Exception {
        int databaseSizeBeforeCreate = dMSRecordRepository.findAll().size();

        // Create the DMSRecord
        DMSRecordDTO dMSRecordDTO = dMSRecordMapper.toDto(dMSRecord);
        restDMSRecordMockMvc.perform(post("/api/dms-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dMSRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the DMSRecord in the database
        List<DMSRecord> dMSRecordList = dMSRecordRepository.findAll();
        assertThat(dMSRecordList).hasSize(databaseSizeBeforeCreate + 1);
        DMSRecord testDMSRecord = dMSRecordList.get(dMSRecordList.size() - 1);
        assertThat(testDMSRecord.getPrescriptionRef()).isEqualTo(DEFAULT_PRESCRIPTION_REF);

        // Validate the DMSRecord in Elasticsearch
        verify(mockDMSRecordSearchRepository, times(1)).save(testDMSRecord);
    }

    @Test
    @Transactional
    public void createDMSRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dMSRecordRepository.findAll().size();

        // Create the DMSRecord with an existing ID
        dMSRecord.setId(1L);
        DMSRecordDTO dMSRecordDTO = dMSRecordMapper.toDto(dMSRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDMSRecordMockMvc.perform(post("/api/dms-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dMSRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DMSRecord in the database
        List<DMSRecord> dMSRecordList = dMSRecordRepository.findAll();
        assertThat(dMSRecordList).hasSize(databaseSizeBeforeCreate);

        // Validate the DMSRecord in Elasticsearch
        verify(mockDMSRecordSearchRepository, times(0)).save(dMSRecord);
    }


    @Test
    @Transactional
    public void getAllDMSRecords() throws Exception {
        // Initialize the database
        dMSRecordRepository.saveAndFlush(dMSRecord);

        // Get all the dMSRecordList
        restDMSRecordMockMvc.perform(get("/api/dms-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dMSRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].prescriptionRef").value(hasItem(DEFAULT_PRESCRIPTION_REF)));
    }
    
    @Test
    @Transactional
    public void getDMSRecord() throws Exception {
        // Initialize the database
        dMSRecordRepository.saveAndFlush(dMSRecord);

        // Get the dMSRecord
        restDMSRecordMockMvc.perform(get("/api/dms-records/{id}", dMSRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dMSRecord.getId().intValue()))
            .andExpect(jsonPath("$.prescriptionRef").value(DEFAULT_PRESCRIPTION_REF));
    }

    @Test
    @Transactional
    public void getNonExistingDMSRecord() throws Exception {
        // Get the dMSRecord
        restDMSRecordMockMvc.perform(get("/api/dms-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDMSRecord() throws Exception {
        // Initialize the database
        dMSRecordRepository.saveAndFlush(dMSRecord);

        int databaseSizeBeforeUpdate = dMSRecordRepository.findAll().size();

        // Update the dMSRecord
        DMSRecord updatedDMSRecord = dMSRecordRepository.findById(dMSRecord.getId()).get();
        // Disconnect from session so that the updates on updatedDMSRecord are not directly saved in db
        em.detach(updatedDMSRecord);
        updatedDMSRecord
            .prescriptionRef(UPDATED_PRESCRIPTION_REF);
        DMSRecordDTO dMSRecordDTO = dMSRecordMapper.toDto(updatedDMSRecord);

        restDMSRecordMockMvc.perform(put("/api/dms-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dMSRecordDTO)))
            .andExpect(status().isOk());

        // Validate the DMSRecord in the database
        List<DMSRecord> dMSRecordList = dMSRecordRepository.findAll();
        assertThat(dMSRecordList).hasSize(databaseSizeBeforeUpdate);
        DMSRecord testDMSRecord = dMSRecordList.get(dMSRecordList.size() - 1);
        assertThat(testDMSRecord.getPrescriptionRef()).isEqualTo(UPDATED_PRESCRIPTION_REF);

        // Validate the DMSRecord in Elasticsearch
        verify(mockDMSRecordSearchRepository, times(1)).save(testDMSRecord);
    }

    @Test
    @Transactional
    public void updateNonExistingDMSRecord() throws Exception {
        int databaseSizeBeforeUpdate = dMSRecordRepository.findAll().size();

        // Create the DMSRecord
        DMSRecordDTO dMSRecordDTO = dMSRecordMapper.toDto(dMSRecord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDMSRecordMockMvc.perform(put("/api/dms-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dMSRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DMSRecord in the database
        List<DMSRecord> dMSRecordList = dMSRecordRepository.findAll();
        assertThat(dMSRecordList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DMSRecord in Elasticsearch
        verify(mockDMSRecordSearchRepository, times(0)).save(dMSRecord);
    }

    @Test
    @Transactional
    public void deleteDMSRecord() throws Exception {
        // Initialize the database
        dMSRecordRepository.saveAndFlush(dMSRecord);

        int databaseSizeBeforeDelete = dMSRecordRepository.findAll().size();

        // Delete the dMSRecord
        restDMSRecordMockMvc.perform(delete("/api/dms-records/{id}", dMSRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DMSRecord> dMSRecordList = dMSRecordRepository.findAll();
        assertThat(dMSRecordList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DMSRecord in Elasticsearch
        verify(mockDMSRecordSearchRepository, times(1)).deleteById(dMSRecord.getId());
    }

    @Test
    @Transactional
    public void searchDMSRecord() throws Exception {
        // Initialize the database
        dMSRecordRepository.saveAndFlush(dMSRecord);
        when(mockDMSRecordSearchRepository.search(queryStringQuery("id:" + dMSRecord.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dMSRecord), PageRequest.of(0, 1), 1));
        // Search the dMSRecord
        restDMSRecordMockMvc.perform(get("/api/_search/dms-records?query=id:" + dMSRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dMSRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].prescriptionRef").value(hasItem(DEFAULT_PRESCRIPTION_REF)));
    }
}
