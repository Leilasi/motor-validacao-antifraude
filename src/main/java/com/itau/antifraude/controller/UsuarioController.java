package com.itau.antifraude.controller;

import com.itau.antifraude.dto.request.UsuarioRequest;
import com.itau.antifraude.dto.response.UsuarioResponse;
import com.itau.antifraude.service.UsuarioService;
import com.itau.antifraude.service.exception.UsuarioInvalidoException;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Operation(description = "Gera uma nota aleatória de confiabilidade entre 0 e 10.")
    @PostMapping
    public ResponseEntity<String> validarUsuario(@RequestBody UsuarioRequest usuarioDto) {
        try {
            return ResponseEntity.ok(usuarioService.validarUsuario(usuarioDto));
        } catch (UsuarioInvalidoException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(description = "Cadastra um novo usuário no sistema")
    @PostMapping("/salvar")
    public ResponseEntity<?> salvarUsuario(@RequestBody UsuarioRequest usuarioDto) {
        try {
            UsuarioResponse usuarioResponse = usuarioService.salvarUsuario(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
        } catch (UsuarioInvalidoException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
