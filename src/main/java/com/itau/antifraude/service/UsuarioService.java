package com.itau.antifraude.service;

import com.itau.antifraude.dto.request.EnderecoRequest;
import com.itau.antifraude.dto.request.UsuarioRequest;
import com.itau.antifraude.mapper.UsuarioMapper;
import com.itau.antifraude.model.Usuario;
import com.itau.antifraude.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public int validarUsuario(UsuarioRequest usuarioRequest) {

        if(validarCpf(usuarioRequest.cpf())
                && validarNome(usuarioRequest.nome())
                && validarTelefone(usuarioRequest.telefone())
                && validarEmail(usuarioRequest.email())
                && validarDataNascimento(usuarioRequest.dataNascimento())
                && validarEndereco(usuarioRequest.endereco())){
            return gerarNotaAleatoria();

        }
        return 0;
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

    public Usuario salvarUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);
        return usuarioRepository.save(usuario);
    }

}
