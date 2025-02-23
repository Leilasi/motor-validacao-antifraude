package com.itau.antifraude.service;

import com.itau.antifraude.dto.request.EnderecoRequest;
import com.itau.antifraude.dto.request.UsuarioRequest;
import com.itau.antifraude.dto.response.UsuarioResponse;
import com.itau.antifraude.mapper.UsuarioMapper;
import com.itau.antifraude.model.Usuario;
import com.itau.antifraude.repository.UsuarioRepository;
import com.itau.antifraude.service.exception.ParametroInvalidoException;
import com.itau.antifraude.service.exception.UsuarioInvalidoException;
import com.itau.antifraude.service.exception.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public String validarUsuario(UsuarioRequest usuarioRequest) throws UsuarioInvalidoException {

        if(validarCpf(usuarioRequest.getCpf())
                && validarNome(usuarioRequest.getNome())
                && validarTelefone(usuarioRequest.getTelefone())
                && validarEmail(usuarioRequest.getEmail())
                && validarDataNascimento(usuarioRequest.getDataNascimento())
                && validarEndereco(usuarioRequest.getEndereco())){
            return "Grau de Confiabilidade: " + gerarNotaAleatoria();

        }
        throw new UsuarioInvalidoException("Erro ao validar o usuário. Grau de Confiabilidade: " + 0);
    }

    private int gerarNotaAleatoria(){
        return new Random().nextInt(11);
    }

    private boolean validarCpf(String cpf) {
        return cpf != null && cpf.length() == 11 && cpf.matches("[0-9]*");
    }

    private boolean validarNome(String nome) {
        return nome != null && !nome.isEmpty();
    }

    private boolean validarTelefone(String telefone) {
        return telefone != null && !telefone.isEmpty();
    }

    private boolean validarEmail(String email) {
        return email != null && !email.isEmpty() && email.contains("@");
    }

    private boolean validarDataNascimento(LocalDate dataNascimento) {
        return dataNascimento != null;
    }

    private boolean validarEndereco(EnderecoRequest endereco) {
        return endereco != null;
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuarioRequest) throws UsuarioInvalidoException {
        validarUsuario(usuarioRequest);
        if (usuarioRepository.existsByCpf(usuarioRequest.getCpf())) {
            throw new UsuarioInvalidoException("Já existe usuario cadastrado com cnpj informado");
        }

        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new UsuarioInvalidoException("Já existe usuario cadastrado com o email informado");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);
        usuario.setNotaConfiabilidade(gerarNotaAleatoria());
        return usuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }

    public Page<UsuarioResponse> obterUsuarios(Integer page, Integer linesPerPage) {
        Pageable pageable = PageRequest.of(page, linesPerPage);
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarioMapper.toResponseDTOPage(usuarios);
    }

    public UsuarioResponse obterUsuarioPorCpf(String cpf) throws UsuarioNaoEncontradoException, ParametroInvalidoException {
        if (cpf == null || cpf.isEmpty()) {
            throw new ParametroInvalidoException("CPF informado está nulo ou vazio");
        }
        Usuario usuario = usuarioRepository.findByCpf(cpf);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com CPF informado");
        }
        return usuarioMapper.toResponseDTO(usuario);
    }


}
