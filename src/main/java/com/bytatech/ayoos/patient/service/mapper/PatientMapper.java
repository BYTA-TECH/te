package com.bytatech.ayoos.patient.service.mapper;

import com.bytatech.ayoos.patient.domain.*;
import com.bytatech.ayoos.patient.service.dto.PatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Patient} and its DTO {@link PatientDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {


    @Mapping(target = "medicalCases", ignore = true)
    @Mapping(target = "removeMedicalCases", ignore = true)
    Patient toEntity(PatientDTO patientDTO);

    default Patient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(id);
        return patient;
    }
}
