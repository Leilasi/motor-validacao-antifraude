package com.itau.antifraude.controller;

import com.itau.antifraude.dto.request.UsuarioRequest;
import com.itau.antifraude.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public String validarUsuario(@RequestBody UsuarioRequest usuarioDto) {
        int nota = usuarioService.validarUsuario(usuarioDto);
        usuarioService.salvarUsuario(usuarioDto);
        return "Grau de Confiabilidade: " + nota;
    }
}
