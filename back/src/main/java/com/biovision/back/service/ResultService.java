package com.biovision.back.service;

import com.biovision.back.dto.ResultDTO;

import java.util.List;
import java.util.UUID;

public interface ResultService {
    List<ResultDTO> findAll();
    ResultDTO findById(UUID id);
    ResultDTO save(ResultDTO resultDTO);
    ResultDTO update(UUID id, ResultDTO resultDTO);
    void delete(UUID id);
}
