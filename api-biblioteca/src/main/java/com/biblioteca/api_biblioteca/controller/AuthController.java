package com.biblioteca.api_biblioteca.controller;

import com.biblioteca.api_biblioteca.dto.LoginDTO;
import com.biblioteca.api_biblioteca.model.Usuario;
import com.biblioteca.api_biblioteca.repository.UsuarioRepository;
import com.biblioteca.api_biblioteca.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para login e obtenção de token")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Realizar Login", description = "Recebe email e senha, valida no banco e retorna um Token JWT para uso nas requisições protegidas.")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginData) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuTxEmail(loginData.getUsuTxEmail());

        if(usuarioOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }

        Usuario usuario = usuarioOpt.get();

        boolean senhaConfere = passwordEncoder.matches(loginData.getUsuTxSenha(), usuario.getUsuTxSenha());

        if(!senhaConfere){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }

        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }
}
