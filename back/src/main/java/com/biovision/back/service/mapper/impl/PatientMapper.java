package com.biovision.back.service.mapper.impl;

import com.biovision.back.dto.DiagnosisDTO;
import com.biovision.back.dto.DoctorDTO;
import com.biovision.back.dto.ExaminationDTO;
import com.biovision.back.dto.PatientDTO;
import com.biovision.back.entity.Diagnosis;
import com.biovision.back.entity.Doctor;
import com.biovision.back.entity.Examination;
import com.biovision.back.entity.Patient;
import com.biovision.back.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Lazy
@Mapper(componentModel = "spring")
public interface PatientMapper extends EntityMapper<Patient, PatientDTO> {
    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "doctors", target = "doctors", qualifiedByName = "toDTOForDoctorList")
    @Mapping(source = "diagnoses", target = "diagnoses", qualifiedByName = "toDtoForDiagnosesList")
    @Mapping(source = "examinations", target = "examinations", qualifiedByName = "toDtoForExaminationsList")
    PatientDTO toDTO(Patient entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "doctors", target = "doctors", qualifiedByName = "toEntityForDoctorList")
    @Mapping(source = "diagnoses", target = "diagnoses", qualifiedByName = "toEntityForDiagnosesList")
    @Mapping(source = "examinations", target = "examinations", qualifiedByName = "toEntityForExaminationsList")
    Patient toEntity(PatientDTO dto);

    @Named("toDTOForDoctorList")
    List<DoctorDTO> toDTOForDoctorList(List<Doctor> doctors);

    @Named("toEntityForDoctorList")
    List<Doctor> toEntityForDoctorList(List<DoctorDTO> doctors);

    @Named("toDtoForDiagnosesList")
    List<DiagnosisDTO> toDtoForDiagnosesList(List<Diagnosis> diagnoses);

    @Named("toEntityForDiagnosesList")
    List<Diagnosis> toEntityForDiagnosesList(List<DiagnosisDTO> diagnoses);

    @Named("toDtoForExaminationsList")
    List<ExaminationDTO> toDtoForExaminationsList(List<Examination> examinations);

    @Named("toEntityForExaminationsList")
    List<Examination> toEntityForExaminationsList(List<ExaminationDTO> examinations);
}
