package com.bytatech.ayoos.patient.service.mapper;

import com.bytatech.ayoos.patient.domain.*;
import com.bytatech.ayoos.patient.service.dto.MedicalCaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicalCase} and its DTO {@link MedicalCaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicalCaseMapper extends EntityMapper<MedicalCaseDTO, MedicalCase> {


    @Mapping(target = "dmsRecords", ignore = true)
    @Mapping(target = "removeDmsRecords", ignore = true)
    MedicalCase toEntity(MedicalCaseDTO medicalCaseDTO);

    default MedicalCase fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalCase medicalCase = new MedicalCase();
        medicalCase.setId(id);
        return medicalCase;
    }
}
