package com.biblioteca.api_biblioteca.service;
import com.biblioteca.api_biblioteca.model.Usuario;
import com.biblioteca.api_biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public Usuario salvarUsuario(Usuario usuario){
        if (usuarioRepository.findByUsuTxEmail(usuario.getUsuTxEmail()).isPresent()) {
            throw new RuntimeException("Este email já está cadastrado!");
        }
        String senhaCriptografada = passwordEncoder.encode(usuario.getUsuTxSenha());
        usuario.setUsuTxSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    public void processarEsqueciSenha(String email) {
        Usuario usuario = usuarioRepository.findByUsuTxEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado."));

        String token = UUID.randomUUID().toString();

        LocalDateTime validade = LocalDateTime.now().plusMinutes(30);

        usuario.setResetToken(token);
        usuario.setTokenExpiryDate(validade);

        usuarioRepository.save(usuario);

        emailService.sendEmail(email,token);
    }

    public void resetPassword(String token, String newPassword){
        Usuario usuario = usuarioRepository.findByResetToken(token)
                .orElseThrow(()-> new RuntimeException("Token inválido ou não encontrado!"));
        if(usuario.getTokenExpiryDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("O link de recuperação expirou!");
        }

        usuario.setUsuTxSenha(passwordEncoder.encode(newPassword));

        usuario.setResetToken(null);
        usuario.setTokenExpiryDate(null);

        usuarioRepository.save(usuario);
    }
}