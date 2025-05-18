package com.biovision.back.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder public class ExaminationDTO {
    private UUID id;
    private Instant date;
    private ResultDTO result;
    private DiagnosisDTO diagnosis;
}
