package com.biovision.back.service;

import com.biovision.back.dto.DiagnosisDTO;
import com.biovision.back.dto.request.DiagnosisRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiagnosisService {
    List<DiagnosisDTO> findAll();
    Optional<DiagnosisDTO> findById(UUID id);
    Optional<DiagnosisDTO> findByName(String name);
    DiagnosisDTO save(DiagnosisRequest diagnosisRequest);
    DiagnosisDTO update(UUID id, DiagnosisDTO diagnosisDTO);
    void delete(UUID id);
}
