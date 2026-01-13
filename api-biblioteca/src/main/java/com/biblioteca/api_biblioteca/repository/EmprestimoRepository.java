package com.biblioteca.api_biblioteca.repository;

import com.biblioteca.api_biblioteca.model.Emprestimo;
import com.biblioteca.api_biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository  extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByUsuNr(Usuario usuario);
}
