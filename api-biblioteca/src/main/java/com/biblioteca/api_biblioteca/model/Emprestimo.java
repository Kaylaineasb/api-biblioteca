package com.biblioteca.api_biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empNrId;

    @ManyToOne
    @JoinColumn(name = "usuNrId", nullable = false)
    private Usuario usuNr;

    @ManyToOne
    @JoinColumn(name = "livNrId",nullable = false)
    private Livro livNr;

    @Column(name = "empDtEmprestimo")
    private LocalDate empDtEmprestimo;

    @Column(name = "empDtDevolucaoPrevista")
    private LocalDate empDtDevolucaoPrevista;

    @Column(name = "empDtDevolucaoReal")
    private LocalDate empDtDevolucaoReal;

    @Enumerated(EnumType.STRING)
    @Column(name = "empTxStatus")
    private StatusEmprestimo empTxStatus;

    @Column(name = "empNrRenovacoes")
    private Integer empNrRenovacoes = 0;
}
