package com.biblioteca.api_biblioteca.controller;

import com.biblioteca.api_biblioteca.model.Usuario;
import com.biblioteca.api_biblioteca.repository.UsuarioRepository;
import com.biblioteca.api_biblioteca.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuários", description = "Gerenciamento de clientes e administradores")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Operation(summary = "Cadastrar Usuário", description = "Cria uma nova conta de acesso (Cliente ou Admin).")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario){
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping
    @Operation(summary = "Listar Usuários", description = "Retorna lista paginada de usuários.")
    public ResponseEntity<Page<Usuario>> listarUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String q // Filtro de busca
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("usuNrId").descending());

        return ResponseEntity.ok(usuarioService.listar(q, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Retorna os dados de um usuário específico.")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir Usuário", description = "Remove um usuário do sistema.")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar Usuário", description = "Atualiza nome ou email de um usuário.")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado){
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setUsuTxNome(usuarioAtualizado.getUsuTxNome());
                    usuarioExistente.setUsuTxEmail(usuarioAtualizado.getUsuTxEmail());

                    Usuario salvo = usuarioService.salvarUsuario(usuarioExistente);
                    return ResponseEntity.ok(salvo);
        })
                .orElse(ResponseEntity.notFound().build());
    }
}