package com.bytatech.ayoos.patient.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MedicalCaseSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MedicalCaseSearchRepositoryMockConfiguration {

    @MockBean
    private MedicalCaseSearchRepository mockMedicalCaseSearchRepository;

}
