package com.itau.antifraude.config.swagger;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SwaggerStartupLogger implements ApplicationListener<ApplicationReadyEvent> {

    private final Environment environment;

    public SwaggerStartupLogger(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String swaggerUrl = "http://localhost:" + port + contextPath + "/swagger-ui/index.html";

        System.out.println("\n----------------------------------------------------------");
        System.out.println("Swagger UI disponível em: " + swaggerUrl);
        System.out.println("----------------------------------------------------------\n");

    }
}
