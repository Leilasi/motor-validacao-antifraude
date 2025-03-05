package com.itau.antifraude.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
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
        private LocalDate dataNascimento;

        @Column(nullable = false)
        private String nomeMae;

        @Column(nullable = false)
        private Integer notaConfiabilidade;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "endereco_id", nullable = false, referencedColumnName = "id")
        private Endereco endereco;

        public Usuario() {
        }

        public Usuario(Long id, String cpf, String nome, String telefone, String email, LocalDate dataNascimento, String nomeMae, Integer notaConfiabilidade, Endereco endereco) {
                this.id = id;
                this.cpf = cpf;
                this.nome = nome;
                this.telefone = telefone;
                this.email = email;
                this.dataNascimento = dataNascimento;
                this.nomeMae = nomeMae;
                this.notaConfiabilidade = notaConfiabilidade;
                this.endereco = endereco;
        }

}

