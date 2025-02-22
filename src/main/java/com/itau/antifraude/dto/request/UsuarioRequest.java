package com.itau.antifraude.dto.request;

import java.time.LocalDate;

public record UsuarioRequest(

        String cpf,
        String nome,
        String telefone,
        String email,
        LocalDate dataNascimento,
        EnderecoRequest endereco,
        String nomeMae) {
}
