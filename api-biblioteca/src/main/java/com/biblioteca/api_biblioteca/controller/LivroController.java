package com.biblioteca.api_biblioteca.controller;

import com.biblioteca.api_biblioteca.dto.LivroDTO;
import com.biblioteca.api_biblioteca.model.Livro;
import com.biblioteca.api_biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livros")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Livros", description = "Gerenciamento do acervo bibliográfico")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    @Operation(summary = "Cadastrar Livro", description = "Adiciona um novo livro ao banco de dados (Requer perfil ADMIN).")
    public ResponseEntity<Livro> cadastrar(@RequestBody Livro livro){
        Livro novoLivro = livroService.salvar(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes de um livro específico.")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id){
        Optional<Livro> livro = livroService.buscarPorId(id);
        return  livro.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir Livro", description = "Remove um livro do sistema (Requer perfil ADMIN).")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Livro", description = "Atualiza os dados (título, autor, ISBN) de um livro existente.")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livroAtualizado){
        return livroService.buscarPorId(id)
                .map(livro -> {
                    livro.setLivTxTitulo(livroAtualizado.getLivTxTitulo());
                    livro.setLivTxAutor(livroAtualizado.getLivTxAutor());
                    livro.setLivTxIsbn(livroAtualizado.getLivTxIsbn());
                    livro.setLivDtPublicacao(livroAtualizado.getLivDtPublicacao());

                    Livro livroSalvo = livroService.salvar(livro);
                    return ResponseEntity.ok(livroSalvo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<LivroDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String q) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(livroService.listar(q, pageable));
    }
}
