package com.biblioteca.api_biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "livNrId")
    private long livNrId;

    @Column(name = "livTxTitulo", nullable = false)
    private String livTxTitulo;

    @Column(name = "livTxAutor",nullable = false)
    private String livTxAutor;

    @Column(name = "livTxIsbn", unique = true)
    private String livTxIsbn;

    @Column(name = "livDtPublicacao")
    private LocalDate livDtPublicacao;
}
