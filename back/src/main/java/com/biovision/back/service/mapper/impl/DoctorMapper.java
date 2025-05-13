package com.biovision.back.service.mapper.impl;

import com.biovision.back.dto.DoctorDTO;
import com.biovision.back.dto.PatientDTO;
import com.biovision.back.entity.Doctor;
import com.biovision.back.entity.Patient;
import com.biovision.back.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper extends EntityMapper<Doctor, DoctorDTO> {
    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "patients", target = "patients", qualifiedByName = "patientListToDTO")
    DoctorDTO toDTO(Doctor entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "patients", target = "patients", qualifiedByName = "patientListToEntity")
    Doctor toEntity(DoctorDTO dto);

    @Named("patientListToDTO")
    List<PatientDTO> patientListToDTO(List<Patient> patientList);

    @Named("patientListToEntity")
    List<Patient> patientListToEntity(List<PatientDTO> patientList);
}
