package com.itau.antifraude.dto.request;

public record EnderecoRequest(

         String rua,
         String bairro,
         String cidade,
         String estado,
         String cep ) {
}
