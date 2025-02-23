package com.itau.antifraude.mapper;

import org.springframework.data.domain.Page;

import java.util.List;

public interface GenericMapper<ENTIDADE, REQUEST, RESPONSE> {
    ENTIDADE toEntity(REQUEST request);
    RESPONSE toResponseDTO(ENTIDADE entity);
    List<RESPONSE> toResponseDTOList(List<ENTIDADE> entities);
    default Page<RESPONSE> toResponseDTOPage(Page<ENTIDADE> entities) {
        return entities.map(this::toResponseDTO);
    }
}
