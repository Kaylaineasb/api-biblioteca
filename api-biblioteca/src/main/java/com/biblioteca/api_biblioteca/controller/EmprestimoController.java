package com.biblioteca.api_biblioteca.controller;

import com.biblioteca.api_biblioteca.dto.EmprestimoDTO;
import com.biblioteca.api_biblioteca.model.Emprestimo;
import com.biblioteca.api_biblioteca.model.Usuario;
import com.biblioteca.api_biblioteca.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<Emprestimo> criar(@RequestBody EmprestimoDTO dto) {
        Emprestimo novoEmprestimo = emprestimoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmprestimo);
    }

    @GetMapping
    public List<Emprestimo> listar(){
        return emprestimoService.listarTodos();
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<Void> devolver(@PathVariable Long id) {
        emprestimoService.realizarDevolucao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/meus-emprestimos")
    public List<Emprestimo> meusEmprestimos() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = (Usuario) auth.getPrincipal();

        return  emprestimoService.listarPorUsuario(usuarioLogado);
    }

}
