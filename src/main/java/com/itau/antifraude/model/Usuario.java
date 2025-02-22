package com.itau.antifraude.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
        private static final long serialVersionUID = 1L;

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

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "endereco_id", nullable = false, referencedColumnName = "id")
        private Endereco endereco;

        @Column(nullable = false)
        private String nomeMae;

}

