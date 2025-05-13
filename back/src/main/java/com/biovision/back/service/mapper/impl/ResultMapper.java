package com.biovision.back.service.mapper.impl;

import com.biovision.back.dto.ResultDTO;
import com.biovision.back.entity.Result;
import com.biovision.back.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResultMapper extends EntityMapper<Result, ResultDTO> {
    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "hasCancer", target = "hasCancer")
    @Mapping(source = "originalImagePath", target = "originalImagePath")
    @Mapping(source = "resultImagePath", target = "resultImagePath")
    @Mapping(source = "examination", target = "examination")
    ResultDTO toDTO(Result entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "hasCancer", target = "hasCancer")
    @Mapping(source = "originalImagePath", target = "originalImagePath")
    @Mapping(source = "resultImagePath", target = "resultImagePath")
    @Mapping(source = "examination", target = "examination")
    Result toEntity(ResultDTO dto);
}
