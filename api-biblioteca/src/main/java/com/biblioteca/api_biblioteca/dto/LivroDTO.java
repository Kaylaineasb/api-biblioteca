package com.biblioteca.api_biblioteca.dto;

import com.biblioteca.api_biblioteca.model.Livro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDTO {

    private Long livNrId;
    private String livTxTitulo;
    private String livTxAutor;
    private String livTxIsbn;
    private boolean disponivel;

    public LivroDTO() {}

    public LivroDTO(Livro livro) {
        this.livNrId = livro.getLivNrId();
        this.livTxTitulo = livro.getLivTxTitulo();
        this.livTxAutor = livro.getLivTxAutor();
        this.livTxIsbn = livro.getLivTxIsbn();
        this.disponivel = livro.isDisponivel();
    }
}