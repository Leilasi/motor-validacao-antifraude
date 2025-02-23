package com.itau.antifraude.mapper;

import com.itau.antifraude.dto.request.EnderecoRequest;
import com.itau.antifraude.dto.response.EnderecoResponse;
import com.itau.antifraude.model.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper extends GenericMapper<Endereco, EnderecoRequest, EnderecoResponse> {

}
