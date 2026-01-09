package com.biblioteca.api_biblioteca.service;
import com.biblioteca.api_biblioteca.model.Usuario;
import com.biblioteca.api_biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvarUsuario(Usuario usuario){
        if (usuarioRepository.findByUsuTxEmail(usuario.getUsuTxEmail()).isPresent()) {
            throw new RuntimeException("Este email já está cadastrado!");
        }
        String senhaCriptografada = passwordEncoder.encode(usuario.getUsuTxSenha());
        usuario.setUsuTxSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }
}