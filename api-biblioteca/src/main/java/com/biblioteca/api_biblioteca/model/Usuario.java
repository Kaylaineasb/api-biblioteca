package com.biblioteca.api_biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="tb_usuario")
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuNrId")
    private Long usuNrId;

    @Column(name = "usuTxNome", nullable = false)
    private String usuTxNome;

    @Column(name="usuTxEmail", nullable = false, unique = true)
    private String usuTxEmail;

    @Column(name="usuTxSenha", nullable = false)
    private String usuTxSenha;

    @Column(name= "usuTxPerfil", nullable = false)
    private String usuTxPerfil;

    @CreationTimestamp
    @Column(name="usuDtCadastro", updatable = false)
    private LocalDateTime usuDtCadastro;
}