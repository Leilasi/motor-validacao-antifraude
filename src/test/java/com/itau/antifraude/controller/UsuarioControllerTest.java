package com.itau.antifraude.controller;

import com.itau.antifraude.dto.request.UsuarioRequest;
import com.itau.antifraude.dto.response.EnderecoResponse;
import com.itau.antifraude.dto.response.UsuarioResponse;
import com.itau.antifraude.service.UsuarioService;
import com.itau.antifraude.service.exception.ParametroInvalidoException;
import com.itau.antifraude.service.exception.UsuarioInvalidoException;
import com.itau.antifraude.service.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioRequest usuarioRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioRequest = new UsuarioRequest();
        usuarioRequest.setCpf("12345678901");
        usuarioRequest.setNome("Leila Fernanda");
        usuarioRequest.setTelefone("11987654321");
        usuarioRequest.setEmail("leila.fernanda@email.com");
        usuarioRequest.setDataNascimento(LocalDate.of(1995, 6, 15));
        usuarioRequest.setNomeMae("Maria Fernanda");
        usuarioRequest.setEndereco(null);
    }

    @Test
    void deveValidarUsuarioComSucesso() throws Exception {

        String respostaEsperada = "Usuário validado com nota de confiabilidade: 8";
        when(usuarioService.validarUsuario(usuarioRequest)).thenReturn(respostaEsperada);


        ResponseEntity<String> response = usuarioController.validarUsuario(usuarioRequest);


        assertEquals(OK, response.getStatusCode());
        assertEquals(respostaEsperada, response.getBody());
        verify(usuarioService, times(1)).validarUsuario(usuarioRequest);
    }

    @Test
    void deveRetornarBadRequestQuandoUsuarioForInvalido() throws Exception {

        String mensagemErro = "Usuário inválido!";
        when(usuarioService.validarUsuario(usuarioRequest)).thenThrow(new UsuarioInvalidoException(mensagemErro));


        ResponseEntity<String> response = usuarioController.validarUsuario(usuarioRequest);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(mensagemErro, response.getBody());
        verify(usuarioService, times(1)).validarUsuario(usuarioRequest);
    }

    @Test
    void deveSalvarUsuarioComSucesso() throws UsuarioInvalidoException {

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setCpf("12345678901");
        usuarioRequest.setNome("Leila Fernanda");
        usuarioRequest.setTelefone("11987654321");
        usuarioRequest.setEmail("leila.fernanda@email.com");
        usuarioRequest.setDataNascimento(LocalDate.of(1995, 6, 15));
        usuarioRequest.setNomeMae("Maria Fernanda");
        usuarioRequest.setEndereco(null);


        Long id = 1L;
        Integer notaConfiabilidade = 8;
        EnderecoResponse enderecoResponse = null;

        UsuarioResponse usuarioResponse = new UsuarioResponse(
                id,
                "12345678901",
                "Leila Fernanda",
                "11987654321",
                "leila.fernanda@email.com",
                LocalDate.of(1995, 6, 15),
                "Maria Fernanda",
                notaConfiabilidade,
                enderecoResponse
        );


        when(usuarioService.salvarUsuario(usuarioRequest)).thenReturn(usuarioResponse);


        ResponseEntity<?> response = usuarioController.salvarUsuario(usuarioRequest);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(usuarioResponse, response.getBody());
        verify(usuarioService, times(1)).salvarUsuario(usuarioRequest);
    }

    @Test
    void deveRetornarBadRequestQuandoUsuarioInvalidoForSalvo() throws UsuarioInvalidoException {

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setCpf("12345678901");
        usuarioRequest.setNome("Leila Fernanda");
        usuarioRequest.setTelefone("11987654321");
        usuarioRequest.setEmail("leila.fernanda@email.com");
        usuarioRequest.setDataNascimento(LocalDate.of(1995, 6, 15));
        usuarioRequest.setNomeMae("Maria Fernanda");
        usuarioRequest.setEndereco(null);


        when(usuarioService.salvarUsuario(usuarioRequest)).thenThrow(new UsuarioInvalidoException("Usuário inválido"));


        ResponseEntity<?> response = usuarioController.salvarUsuario(usuarioRequest);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuário inválido", response.getBody());
        verify(usuarioService, times(1)).salvarUsuario(usuarioRequest);
    }

    @Test
    void deveObterUsuariosComSucesso() {

        Integer page = 0;
        Integer linesPerPage = 20;
        Page<UsuarioResponse> usuariosDTO = mock(Page.class);  // Mock da Page

        when(usuarioService.obterUsuarios(page, linesPerPage)).thenReturn(usuariosDTO);


        ResponseEntity<?> response = usuarioController.obterUsuarios(page, linesPerPage);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuariosDTO, response.getBody());
        verify(usuarioService, times(1)).obterUsuarios(page, linesPerPage);
    }

    @Test
    void deveObterUsuarioPorCpfComSucesso() throws UsuarioNaoEncontradoException, ParametroInvalidoException {

        String cpf = "12345678901";

        UsuarioResponse usuarioResponse = new UsuarioResponse(
                1L,
                cpf,
                "Leila Fernanda",
                "11987654321",
                "leila.fernanda@email.com",
                LocalDate.of(1995, 6, 15),
                "Maria Fernanda",
                8,
                null
        );

        when(usuarioService.obterUsuarioPorCpf(cpf)).thenReturn(usuarioResponse);


        ResponseEntity<?> response = usuarioController.obterUsuarioporCpf(cpf);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioResponse, response.getBody());
        verify(usuarioService, times(1)).obterUsuarioPorCpf(cpf);
    }

    @Test
    void deveRetornarNotFoundQuandoUsuarioNaoExistir() throws UsuarioNaoEncontradoException, ParametroInvalidoException {

        String cpf = "12345678901";
        when(usuarioService.obterUsuarioPorCpf(cpf)).thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado"));


        ResponseEntity<?> response = usuarioController.obterUsuarioporCpf(cpf);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, times(1)).obterUsuarioPorCpf(cpf);
    }

    @Test
    void deveRetornarBadRequestQuandoParametroInvalido() throws UsuarioNaoEncontradoException, ParametroInvalidoException {

        String cpf = "12345678901";
        String mensagemErro = "Parâmetro inválido!";
        when(usuarioService.obterUsuarioPorCpf(cpf)).thenThrow(new ParametroInvalidoException(mensagemErro));


        ResponseEntity<?> response = usuarioController.obterUsuarioporCpf(cpf);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(mensagemErro, response.getBody());
        verify(usuarioService, times(1)).obterUsuarioPorCpf(cpf);
    }
}
