package com.bytatech.ayoos.patient.repository;

import com.bytatech.ayoos.patient.domain.MedicalCase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalCaseRepository extends JpaRepository<MedicalCase, Long> {

}
