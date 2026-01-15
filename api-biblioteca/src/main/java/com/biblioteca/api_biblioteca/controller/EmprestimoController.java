package com.biblioteca.api_biblioteca.controller;

import com.biblioteca.api_biblioteca.dto.EmprestimoDTO;
import com.biblioteca.api_biblioteca.model.Emprestimo;
import com.biblioteca.api_biblioteca.model.Usuario;
import com.biblioteca.api_biblioteca.service.EmprestimoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Empréstimos", description = "Gerenciamento de empréstimos e devoluções")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping
    @Operation(summary = "Novo Empréstimo", description = "Registra o empréstimo de um livro para um usuário. Verifica disponibilidade automaticamente.")
    public ResponseEntity<Emprestimo> criar(@RequestBody EmprestimoDTO dto) {
        Emprestimo novoEmprestimo = emprestimoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmprestimo);
    }

    @GetMapping
    @Operation(summary = "Listar Todos", description = "Retorna a lista completa de empréstimos (Histórico).")
    public List<Emprestimo> listar(){
        return emprestimoService.listarTodos();
    }

    @PutMapping("/{id}/devolucao")
    @Operation(summary = "Realizar Devolução", description = "Finaliza um empréstimo ativo e libera o livro.")
    public ResponseEntity<Void> devolver(@PathVariable Long id) {
        emprestimoService.realizarDevolucao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/meus-emprestimos")
    @Operation(summary = "Meus Empréstimos", description = "Lista apenas os empréstimos do usuário logado (baseado no Token).")
    public List<Emprestimo> meusEmprestimos() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = (Usuario) auth.getPrincipal();

        return  emprestimoService.listarPorUsuario(usuarioLogado);
    }

    @PutMapping("/{id}/renovacao")
    @Operation(summary = "Renovar Livro", description = "Estende o prazo de entrega por +7 dias. Máximo de 3 renovações permitidas.")
    public ResponseEntity<Void> renovar(@PathVariable Long id) {
        emprestimoService.renovar(id);
        return ResponseEntity.noContent().build();
    }

}
