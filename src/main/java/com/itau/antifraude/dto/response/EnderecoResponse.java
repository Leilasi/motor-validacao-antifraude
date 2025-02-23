package com.itau.antifraude.dto.response;

public record EnderecoResponse(

         Long id,
         String rua,
         String bairro,
         String cidade,
         String estado,
         String cep ) {
}
