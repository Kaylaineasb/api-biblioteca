package com.biblioteca.api_biblioteca.repository;

import com.biblioteca.api_biblioteca.model.Emprestimo;
import com.biblioteca.api_biblioteca.model.Livro;
import com.biblioteca.api_biblioteca.model.StatusEmprestimo;
import com.biblioteca.api_biblioteca.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EmprestimoRepository  extends JpaRepository<Emprestimo, Long> {
    Page<Emprestimo> findByUsuNr(Usuario usuario, Pageable pageable);

    boolean existsByLivNrAndEmpTxStatus(Livro livro, StatusEmprestimo status);

    long countByEmpTxStatusAndEmpDtDevolucaoPrevistaBefore(StatusEmprestimo status, LocalDate data);
}
