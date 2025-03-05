package com.itau.antifraude.service;

import com.itau.antifraude.dto.request.EnderecoRequest;
import com.itau.antifraude.dto.request.UsuarioRequest;
import com.itau.antifraude.dto.response.EnderecoResponse;
import com.itau.antifraude.dto.response.UsuarioResponse;
import com.itau.antifraude.mapper.UsuarioMapper;
import com.itau.antifraude.model.Endereco;
import com.itau.antifraude.model.Usuario;
import com.itau.antifraude.repository.UsuarioRepository;
import com.itau.antifraude.service.exception.ParametroInvalidoException;
import com.itau.antifraude.service.exception.UsuarioInvalidoException;
import com.itau.antifraude.service.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    private UsuarioRequest usuarioRequest;

    private Usuario usuario;

    private UsuarioResponse usuarioResponse;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        usuarioRequest = new UsuarioRequest();
        usuarioRequest.setCpf("12345678901");
        usuarioRequest.setNome("Leila Fernanda");

        usuarioRequest.setTelefone("11987654321");
        usuarioRequest.setEmail("leila.fernanda@email.com");
        usuarioRequest.setDataNascimento(LocalDate.of(1995, 6, 15));
        usuarioRequest.setNomeMae("Maria Fernanda");

        EnderecoRequest enderecoRequest = new EnderecoRequest();
        enderecoRequest.setRua("Avenida Paulista");
        enderecoRequest.setBairro("Bela Vista");
        enderecoRequest.setCep("01311-000");
        enderecoRequest.setCidade("São Paulo");
        enderecoRequest.setEstado("SP");

        usuarioRequest.setEndereco(enderecoRequest);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf(usuarioRequest.getCpf());
        usuario.setNome(usuarioRequest.getNome());
        usuario.setTelefone(usuarioRequest.getTelefone());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setDataNascimento(usuarioRequest.getDataNascimento());
        usuario.setNomeMae(usuarioRequest.getNomeMae());

        Endereco endereco = new Endereco();
        endereco.setRua(enderecoRequest.getRua());
        endereco.setBairro(enderecoRequest.getBairro());
        endereco.setCep(enderecoRequest.getCep());
        endereco.setCidade(enderecoRequest.getCidade());
        endereco.setEstado(enderecoRequest.getEstado());

        usuario.setEndereco(endereco);

        usuarioResponse = new UsuarioResponse(
                usuario.getId(),
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getEmail(),
                usuario.getDataNascimento(),
                usuario.getNomeMae(),
                usuario.getNotaConfiabilidade(),
                new EnderecoResponse(
                        endereco.getId(),
                        endereco.getRua(),
                        endereco.getBairro(),
                        endereco.getCidade(),
                        endereco.getEstado(),
                        endereco.getCep()
                )
        );

    }

    @Test
    void deveValidarUsuarioComSucesso() throws UsuarioInvalidoException {

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setCpf("12345678901");
        usuarioRequest.setNome("Leila Fernanda");
        usuarioRequest.setTelefone("11987654321");
        usuarioRequest.setEmail("leila.fernanda@email.com");
        usuarioRequest.setDataNascimento(LocalDate.of(1995, 6, 15));
        usuarioRequest.setEndereco(new EnderecoRequest());


        UsuarioService usuarioServiceSpy = spy(usuarioService);
        doReturn(true).when(usuarioServiceSpy).validarCpf(anyString());
        doReturn(true).when(usuarioServiceSpy).validarNome(anyString());
        doReturn(true).when(usuarioServiceSpy).validarTelefone(anyString());
        doReturn(true).when(usuarioServiceSpy).validarEmail(anyString());
        doReturn(true).when(usuarioServiceSpy).validarDataNascimento(any(LocalDate.class));
        doReturn(true).when(usuarioServiceSpy).validarEndereco(any());


        String resultado = usuarioServiceSpy.validarUsuario(usuarioRequest);


        assertTrue(resultado.startsWith("Grau de Confiabilidade: "));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioForInvalido() {
        UsuarioRequest usuarioRequest = this.usuarioRequest;
        usuarioRequest.setCpf("");

        UsuarioService usuarioServiceSpy = spy(usuarioService);
        doReturn(false).when(usuarioServiceSpy).validarCpf(anyString());

        UsuarioInvalidoException exception = assertThrows(
                UsuarioInvalidoException.class,
                () -> usuarioServiceSpy.validarUsuario(usuarioRequest)
        );

        assertEquals("Erro ao validar o usuário. Grau de Confiabilidade: 0", exception.getMessage());
    }

    @Test
    void deveGerarNotaAleatoriaDentroDoIntervalo() {

        Set<Integer> valoresGerados = new HashSet<>();


        for (int i = 0; i < 100; i++) {
            int nota = usuarioService.gerarNotaAleatoria();
            valoresGerados.add(nota);


            assertTrue(nota >= 0 && nota <= 10);
        }


        assertFalse(valoresGerados.isEmpty());
    }

    @Test
    void deveValidarCpfComSucesso() {
        assertTrue(usuarioService.validarCpf("12345678901"));
    }

    @Test
    void deveRetornarFalsoQuandoCpfForNulo() {
        assertFalse(usuarioService.validarCpf(null));
    }

    @Test
    void deveRetornarFalsoQuandoCpfNaoTiver11Digitos() {
        assertFalse(usuarioService.validarCpf("1234567"));
        assertFalse(usuarioService.validarCpf("123456789012"));
    }

    @Test
    void deveRetornarFalsoQuandoCpfContemCaracteresInvalidos() {
        assertFalse(usuarioService.validarCpf("12345abc901"));
    }

    @Test
    void deveValidarNomeComSucesso() {
        assertTrue(usuarioService.validarNome("Leila Fernanda"));
    }

    @Test
    void deveRetornarFalsoQuandoNomeForNuloOuVazio() {
        assertFalse(usuarioService.validarNome(null));
        assertFalse(usuarioService.validarNome(""));
    }

    @Test
    void deveValidarTelefoneComSucesso() {
        assertTrue(usuarioService.validarTelefone("11987654321"));
    }

    @Test
    void deveRetornarFalsoQuandoTelefoneForNuloOuVazio() {
        assertFalse(usuarioService.validarTelefone(null));
        assertFalse(usuarioService.validarTelefone(""));
    }

    @Test
    void deveValidarEmailComSucesso() {
        assertTrue(usuarioService.validarEmail("leila.fernanda@email.com"));
    }

    @Test
    void deveRetornarFalsoQuandoEmailForNuloOuVazio() {
        assertFalse(usuarioService.validarEmail(null));
        assertFalse(usuarioService.validarEmail(""));
    }

    @Test
    void deveRetornarFalsoQuandoEmailNaoContemArroba() {
        assertFalse(usuarioService.validarEmail("leila.fernandaemail.com"));
    }

    @Test
    void deveValidarDataNascimentoComSucesso() {
        assertTrue(usuarioService.validarDataNascimento(LocalDate.of(1995, 6, 15)));
    }

    @Test
    void deveRetornarFalsoQuandoDataNascimentoForNula() {
        assertFalse(usuarioService.validarDataNascimento(null));
    }

    @Test
    void deveValidarEnderecoComSucesso() {
        assertTrue(usuarioService.validarEndereco(new EnderecoRequest()));
    }

    @Test
    void deveRetornarFalsoQuandoEnderecoForNulo() {
        assertFalse(usuarioService.validarEndereco(null));
    }

    @Test
    void deveSalvarUsuarioComSucesso() throws UsuarioInvalidoException {
        // Arrange
        when(usuarioRepository.existsByCpf(usuarioRequest.getCpf())).thenReturn(false);
        when(usuarioRepository.existsByEmail(usuarioRequest.getEmail())).thenReturn(false);
        when(usuarioMapper.toEntity(usuarioRequest)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponseDTO(usuario)).thenReturn(usuarioResponse);

        UsuarioResponse response = usuarioService.salvarUsuario(usuarioRequest);

        assertEquals(usuarioResponse, response);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {
        when(usuarioRepository.existsByCpf(usuarioRequest.getCpf())).thenReturn(true);

        UsuarioInvalidoException exception = assertThrows(
                UsuarioInvalidoException.class,
                () -> usuarioService.salvarUsuario(usuarioRequest)
        );

        assertEquals("Já existe usuario cadastrado com cnpj informado", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaCadastrado() {
        // Arrange
        when(usuarioRepository.existsByCpf(usuarioRequest.getCpf())).thenReturn(false);
        when(usuarioRepository.existsByEmail(usuarioRequest.getEmail())).thenReturn(true);

        // Act & Assert
        UsuarioInvalidoException exception = assertThrows(
                UsuarioInvalidoException.class,
                () -> usuarioService.salvarUsuario(usuarioRequest)
        );

        assertEquals("Já existe usuario cadastrado com o email informado", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveObterUsuariosComSucesso() {
        // Arrange
        int page = 0;
        int linesPerPage = 10;
        Pageable pageable = PageRequest.of(page, linesPerPage);

        Usuario usuario = new Usuario();
        Page<Usuario> usuariosPage = new PageImpl<>(List.of(usuario));

        when(usuarioRepository.findAll(pageable)).thenReturn(usuariosPage);
        when(usuarioMapper.toResponseDTOPage(usuariosPage)).thenReturn(Page.empty());

        // Act
        Page<UsuarioResponse> response = usuarioService.obterUsuarios(page, linesPerPage);

        // Assert
        assertNotNull(response);
        verify(usuarioRepository, times(1)).findAll(pageable);
    }

    @Test
    void deveObterUsuarioPorCpfComSucesso() throws UsuarioNaoEncontradoException, ParametroInvalidoException {
        String cpf = "12345678901";

        when(usuarioRepository.findByCpf(cpf)).thenReturn(usuario);
        when(usuarioMapper.toResponseDTO(usuario)).thenReturn(usuarioResponse);

        UsuarioResponse response = usuarioService.obterUsuarioPorCpf(cpf);

        assertNotNull(response);
        assertEquals(usuarioResponse, response);
        verify(usuarioRepository, times(1)).findByCpf(cpf);
    }

    @Test
    void deveLancarExcecaoQuandoCpfNuloOuVazio() {
        ParametroInvalidoException exception = assertThrows(ParametroInvalidoException.class, () -> {
            usuarioService.obterUsuarioPorCpf("");
        });

        assertEquals("CPF informado está nulo ou vazio", exception.getMessage());

        exception = assertThrows(ParametroInvalidoException.class, () -> {
            usuarioService.obterUsuarioPorCpf(null);
        });

        assertEquals("CPF informado está nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        // Arrange
        String cpf = "12345678901";
        when(usuarioRepository.findByCpf(cpf)).thenReturn(null);

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.obterUsuarioPorCpf(cpf);
        });

        assertEquals("Usuário não encontrado com CPF informado", exception.getMessage());
    }


}