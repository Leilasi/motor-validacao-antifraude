package com.itau.antifraude.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioRequest{

        private String cpf;
        private String nome;
        private String telefone;
        private String email;
        private LocalDate dataNascimento;
        private String nomeMae;
        private EnderecoRequest endereco;

}
