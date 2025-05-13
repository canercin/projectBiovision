package com.biovision.back.dto;

import com.biovision.back.entity.Doctor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PatientDTO extends UserDTO{
    List<DoctorDTO> doctors;
    List<DiagnosisDTO> diagnoses;
    List<ExaminationDTO> examinations;
}
