package com.itau.antifraude.service;

import com.itau.antifraude.model.Usuario;
import com.itau.antifraude.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public int validarUsuario(Usuario  usuario) {
        if (usuario.getCpf() == null || usuario.getNome() == null ||
            usuario.getTelefone() == null || usuario.getEmail() == null ||
            usuario.getDataNascimento() == null || usuario.getEndereco() == null ||
            usuario.getNomeMae() == null) {
            return 0;
        }

        return new Random().nextInt(11);

    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

}
