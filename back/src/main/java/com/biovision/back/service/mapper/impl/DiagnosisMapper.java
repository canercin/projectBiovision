package com.biovision.back.service.mapper.impl;

import com.biovision.back.dto.DiagnosisDTO;
import com.biovision.back.dto.PatientDTO;
import com.biovision.back.entity.Diagnosis;
import com.biovision.back.entity.Patient;
import com.biovision.back.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiagnosisMapper extends EntityMapper<Diagnosis, DiagnosisDTO> {
    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    DiagnosisDTO toDTO(Diagnosis entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "type", target = "type")
    Diagnosis toEntity(DiagnosisDTO dto);
}
