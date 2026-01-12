package com.biblioteca.api_biblioteca.repository;

import com.biblioteca.api_biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByLivTxTituloContaining(String titulo);
}
