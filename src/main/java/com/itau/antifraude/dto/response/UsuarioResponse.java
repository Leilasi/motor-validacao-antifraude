package com.itau.antifraude.dto.response;

import com.itau.antifraude.dto.request.EnderecoRequest;

import java.time.LocalDate;

public record UsuarioResponse(

        Long id,
        String cpf,
        String nome,
        String telefone,
        String email,
        LocalDate dataNascimento,
        String nomeMae,
        Integer notaConfiabilidade,
        EnderecoResponse endereco
        ) {
}
