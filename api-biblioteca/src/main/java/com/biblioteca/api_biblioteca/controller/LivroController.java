package com.biblioteca.api_biblioteca.controller;

import com.biblioteca.api_biblioteca.model.Livro;
import com.biblioteca.api_biblioteca.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> listar(){
        return livroService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Livro> cadastrar(@RequestBody Livro livro){
        Livro novoLivro = livroService.salvar(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id){
        Optional<Livro> livro = livroService.buscarPorId(id);
        return  livro.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
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
}
