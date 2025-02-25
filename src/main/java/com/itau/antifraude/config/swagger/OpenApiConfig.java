package com.itau.antifraude.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Motor Validacão Antifraude")
                        .description("Sistema de validação antifraude")
                        .contact(new Contact().name("Leila Fernanda da Silva").email("leilafernandadasilva@gmail.com"))
                        .version("1.0.0"));
    }
}
