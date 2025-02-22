package com.itau.antifraude.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String cpf;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false)
        private String telefone;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String dataNascimento;

        @Column(nullable = false)
        private String endereco;

        @Column(nullable = false)
        private String nomeMae;

}

