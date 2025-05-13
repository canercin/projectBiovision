package com.biovision.back.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DiagnosisDTO {
    private UUID id;
    private String name;
    private ExaminationDTO examination;
    private Integer type;
    List<PatientDTO> patients;
}
