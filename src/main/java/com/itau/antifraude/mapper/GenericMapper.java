package com.itau.antifraude.mapper;

public interface GenericMapper<ENTIDADE, REQUEST> {
    ENTIDADE toEntity(REQUEST request);
}
