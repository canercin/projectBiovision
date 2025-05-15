package com.biovision.back.service.impl;

import com.biovision.back.dto.DiagnosisDTO;
import com.biovision.back.dto.request.DiagnosisRequest;
import com.biovision.back.entity.Diagnosis;
import com.biovision.back.repository.DiagnosisRepository;
import com.biovision.back.service.DiagnosisService;
import com.biovision.back.service.mapper.impl.DiagnosisMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    private final DiagnosisMapper diagnosisMapper;

    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository, DiagnosisMapper diagnosisMapper) {
        this.diagnosisRepository = diagnosisRepository;
        this.diagnosisMapper = diagnosisMapper;
    }

    @Override
    public List<DiagnosisDTO> findAll() {
        return diagnosisRepository.findAll()
                .stream()
                .map(diagnosisMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<DiagnosisDTO> findById(UUID id) {
        return diagnosisRepository.findById(id)
                .map(diagnosisMapper::toDTO);
    }

    @Override
    public Optional<DiagnosisDTO> findByName(String name) {
        return diagnosisRepository.findByName(name)
                .map(diagnosisMapper::toDTO);
    }

    @Override
    public DiagnosisDTO save(DiagnosisRequest diagnosisRequest) {
        Integer type = Math.toIntExact(diagnosisRepository.count());
        return diagnosisMapper.toDTO(
                diagnosisRepository.save(diagnosisMapper.toEntity(DiagnosisDTO.builder().name(diagnosisRequest.getName()).type(type).build()))
        );
    }

    @Override
    public DiagnosisDTO update(UUID id, DiagnosisDTO diagnosisDTO) {
        Optional<Diagnosis> optionalExistingDiagnosis = diagnosisRepository.findById(id);

        if (optionalExistingDiagnosis.isPresent()) {
            Diagnosis existingDiagnosis = optionalExistingDiagnosis.get();
            existingDiagnosis.setName(diagnosisDTO.getName());
            return diagnosisMapper.toDTO(diagnosisRepository.save(existingDiagnosis));
        }

        throw new EntityNotFoundException("Entity not found");
    }

    @Override
    public void delete(UUID id) {
        diagnosisRepository.deleteById(id);
    }
}
