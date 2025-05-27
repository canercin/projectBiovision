package com.biovision.back.service.mapper.impl;

import com.biovision.back.dto.ExaminationDTO;
import com.biovision.back.dto.ResultDTO;
import com.biovision.back.entity.Examination;
import com.biovision.back.entity.Result;
import com.biovision.back.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExaminationMapper extends EntityMapper<Examination, ExaminationDTO> {
    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "result", target = "result")
    @Mapping(source = "diagnosis", target = "diagnosis")
    ExaminationDTO toDTO(Examination entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "result", target = "result")
    @Mapping(source = "diagnosis", target = "diagnosis")
    Examination toEntity(ExaminationDTO dto);

    @Mapping(target = "examination", source= "examination", ignore = true)
    ResultDTO toResultDTO(Result entity);
}
