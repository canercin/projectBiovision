package com.biovision.back.service.mapper;

public interface EntityMapper<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
}
