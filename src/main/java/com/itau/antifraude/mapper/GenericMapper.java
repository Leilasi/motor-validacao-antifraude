package com.itau.antifraude.mapper;

import java.util.List;

public interface GenericMapper<ENTIDADE, REQUEST, RESPONSE> {
    ENTIDADE toEntity(REQUEST request);
    RESPONSE toResponseDTO(ENTIDADE entity);
    List<RESPONSE> toResponseDTOList(List<ENTIDADE> entities);
}
