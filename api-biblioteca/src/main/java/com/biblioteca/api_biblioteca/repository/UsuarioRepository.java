package com.biblioteca.api_biblioteca.repository;
import com.biblioteca.api_biblioteca.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByUsuTxEmail(String usuTxEmail);
    Optional<Usuario> findByResetToken(String resetToken);
    Page<Usuario> findByUsuTxNomeContainingIgnoreCaseOrUsuTxEmailContainingIgnoreCase(
            String nome, String email, Pageable pageable);
}