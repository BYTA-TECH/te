package com.bytatech.ayoos.patient.repository.search;

import com.bytatech.ayoos.patient.domain.MedicalCase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MedicalCase} entity.
 */
public interface MedicalCaseSearchRepository extends ElasticsearchRepository<MedicalCase, Long> {
}
