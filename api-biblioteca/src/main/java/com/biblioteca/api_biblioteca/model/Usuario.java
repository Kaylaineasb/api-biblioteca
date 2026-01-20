package com.biblioteca.api_biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name="tb_usuario")
public class Usuario implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    @Column(name= "usuTxPerfil", nullable = false)
    private Perfil usuTxPerfil;

    @CreationTimestamp
    @Column(name="usuDtCadastro", updatable = false)
    private LocalDateTime usuDtCadastro;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expiry_date")
    private LocalDateTime tokenExpiryDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.usuTxPerfil == Perfil.ADMIN){
            return List.of(new SimpleGrantedAuthority("ADMIN"));
        }
        return List.of(new SimpleGrantedAuthority("CLIENTE"));
    }

    @Override
    public String getPassword() {
        return usuTxSenha;
    }

    @Override
    public String getUsername() {
        return usuTxNome;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}