package com.biovision.back.service.impl;

import com.biovision.back.dto.ResultDTO;
import com.biovision.back.repository.ResultRepository;
import com.biovision.back.service.ResultService;
import com.biovision.back.service.mapper.impl.ResultMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final ResultMapper resultMapper;

    public ResultServiceImpl(ResultRepository resultRepository, ResultMapper resultMapper) {
        this.resultRepository = resultRepository;
        this.resultMapper = resultMapper;
    }

    @Override
    public List<ResultDTO> findAll() {
        return resultRepository.findAll()
                .stream()
                .map(resultMapper::toDTO)
                .toList();
    }

    @Override
    public ResultDTO findById(UUID id) {
        return resultRepository.findById(id)
                .map(resultMapper::toDTO)
                .orElse(null);
    }

    @Override
    public ResultDTO save(ResultDTO resultDTO) {
        return resultMapper.toDTO(resultRepository.save(resultMapper.toEntity(resultDTO)));
    }

    @Override
    public ResultDTO update(UUID id, ResultDTO resultDTO) {
        // todo will be implemented in the future
        return null;
    }

    @Override
    public void delete(UUID id) {
        resultRepository.deleteById(id);
    }
}
