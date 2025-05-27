package com.biovision.back.service.mapper.impl;

import com.biovision.back.dto.ResultDTO;
import com.biovision.back.entity.Result;
import com.biovision.back.entity.Examination;
import com.biovision.back.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ResultMapper extends EntityMapper<Result, ResultDTO> {
    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "hasCancer", target = "hasCancer")
    @Mapping(source = "originalImagePath", target = "originalImagePath")
    @Mapping(source = "resultImagePath", target = "resultImagePath")
    ResultDTO toDTO(Result entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "hasCancer", target = "hasCancer")
    @Mapping(source = "originalImagePath", target = "originalImagePath")
    @Mapping(source = "resultImagePath", target = "resultImagePath")
    @Mapping(source = "examination.id", target = "examination", qualifiedByName = "examinationFromId")
    Result toEntity(ResultDTO dto);

    @Named("examinationFromId")
    default Examination examinationFromId(UUID id) {
        if (id == null) {
            return null;
        }
        Examination examination = new Examination();
        examination.setId(id);
        return examination;
    }
}
