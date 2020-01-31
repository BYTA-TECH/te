package com.bytatech.ayoos.patient.repository;

import com.bytatech.ayoos.patient.domain.DMSRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DMSRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DMSRecordRepository extends JpaRepository<DMSRecord, Long> {

}
