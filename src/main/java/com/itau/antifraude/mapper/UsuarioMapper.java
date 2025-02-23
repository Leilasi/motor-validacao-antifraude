package com.itau.antifraude.mapper;

import com.itau.antifraude.dto.request.UsuarioRequest;
import com.itau.antifraude.dto.response.UsuarioResponse;
import com.itau.antifraude.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EnderecoMapper.class)
public interface UsuarioMapper extends GenericMapper<Usuario, UsuarioRequest, UsuarioResponse> {
}
