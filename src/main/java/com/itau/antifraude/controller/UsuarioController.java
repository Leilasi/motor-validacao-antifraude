package com.itau.antifraude.controller;

import com.itau.antifraude.dto.request.UsuarioRequest;
import com.itau.antifraude.dto.response.UsuarioResponse;
import com.itau.antifraude.service.UsuarioService;
import com.itau.antifraude.service.exception.ParametroInvalidoException;
import com.itau.antifraude.service.exception.UsuarioInvalidoException;
import com.itau.antifraude.service.exception.UsuarioNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Validar Usuário", description = "Gera uma nota aleatória de confiabilidade entre 0 e 10.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário validado com sucesso", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Usuário inválido", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> validarUsuario(@RequestBody UsuarioRequest usuarioDto) {
        try {
            return ResponseEntity.ok(usuarioService.validarUsuario(usuarioDto));
        } catch (UsuarioInvalidoException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Salvar Usuário", description = "Cadastra um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso", content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Usuário inválido", content = @Content)
    })    @PostMapping("/salvar")
    public ResponseEntity<?> salvarUsuario(@RequestBody UsuarioRequest usuarioDto) {
        try {
            UsuarioResponse usuarioResponse = usuarioService.salvarUsuario(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
        } catch (UsuarioInvalidoException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Obter Usuários", description = "Obtém usuários cadastrados com controle de paginação dos dados retornados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> obterUsuarios(
            @Parameter(description = "Número da página (padrão: 0)", example = "0") @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Número de itens por página (padrão: 20)", example = "20") @RequestParam(defaultValue = "20") Integer linesPerPage) {
        Page<UsuarioResponse> usuariosDTO = usuarioService.obterUsuarios(page, linesPerPage);
        return ResponseEntity.ok(usuariosDTO);
    }

    @Operation(summary = "Obter Usuário por CPF", description = "Obtém um usuário pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso", content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "CPF inválido", content = @Content)
    })
    @GetMapping("/obter-por-cpf")
    public ResponseEntity<?> obterUsuarioporCpf(
            @Parameter(description = "CPF do usuário", example = "12345678901") @RequestParam String cpf) {
        try {
            UsuarioResponse usuarioDTO = usuarioService.obterUsuarioPorCpf(cpf);
            return ResponseEntity.ok(usuarioDTO);
        } catch (UsuarioNaoEncontradoException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ParametroInvalidoException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
