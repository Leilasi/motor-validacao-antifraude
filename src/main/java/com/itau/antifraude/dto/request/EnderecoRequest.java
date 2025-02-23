package com.itau.antifraude.dto.request;

import lombok.Data;

@Data
public class EnderecoRequest{

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
