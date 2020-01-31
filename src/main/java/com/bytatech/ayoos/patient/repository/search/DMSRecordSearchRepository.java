package com.bytatech.ayoos.patient.repository.search;

import com.bytatech.ayoos.patient.domain.DMSRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DMSRecord} entity.
 */
public interface DMSRecordSearchRepository extends ElasticsearchRepository<DMSRecord, Long> {
}
