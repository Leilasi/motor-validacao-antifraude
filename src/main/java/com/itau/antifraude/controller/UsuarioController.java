package com.itau.antifraude.controller;

import com.itau.antifraude.dto.request.UsuarioRequest;
import com.itau.antifraude.dto.response.UsuarioResponse;
import com.itau.antifraude.service.UsuarioService;
import com.itau.antifraude.service.exception.ParametroInvalidoException;
import com.itau.antifraude.service.exception.UsuarioInvalidoException;
import com.itau.antifraude.service.exception.UsuarioNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(description = "Obtém usuários cadastrado com controle de paginação dos dados retornados")
    @GetMapping
    public ResponseEntity<?> obterUsuarios(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "20") Integer linesPerPage) {
        Page<UsuarioResponse> usuariosDTO = usuarioService.obterUsuarios(page, linesPerPage);
        return ResponseEntity.ok(usuariosDTO);
    }

    @Operation(description = "Obtém um usuário pelo CPF")
    @GetMapping("/obter-por-cpf")
    public ResponseEntity<?> obterUsuarioporCpf(@RequestParam String cpf) {
        try {
            UsuarioResponse usuarioDTO = usuarioService.obterUsuarioPorCpf(cpf);
            return ResponseEntity.ok(usuarioDTO);
        } catch (UsuarioNaoEncontradoException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        } catch (ParametroInvalidoException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
