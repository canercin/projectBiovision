package com.biovision.back.service;

import com.biovision.back.dto.ExaminationDTO;
import com.biovision.back.dto.request.ExaminationRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ExaminationService {
    List<ExaminationDTO> findAll();
    ExaminationDTO findById(UUID id);
    ExaminationDTO save(ExaminationRequest examinationRequest) throws IOException;
    ExaminationDTO update(UUID id, ExaminationDTO examinationDTO);
    void delete(UUID id);
    ExaminationDTO findExaminationByPatientId(UUID patientId);
}
