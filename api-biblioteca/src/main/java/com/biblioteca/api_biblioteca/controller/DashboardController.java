package com.biblioteca.api_biblioteca.controller;

import com.biblioteca.api_biblioteca.dto.DashboardDTO;
import com.biblioteca.api_biblioteca.model.StatusEmprestimo;
import com.biblioteca.api_biblioteca.repository.EmprestimoRepository;
import com.biblioteca.api_biblioteca.repository.LivroRepository;
import com.biblioteca.api_biblioteca.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Dashboard", description = "Dados estatísticos e KPIs para a tela inicial do Administrador")
public class DashboardController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @GetMapping
    @Operation(summary = "Obter KPIs", description = "Retorna totais de livros, leitores, empréstimos ativos e empréstimos em atraso.")
    public DashboardDTO getDashboard() {
        DashboardDTO dto = new DashboardDTO();

        dto.setTotalLivros(livroRepository.count());
        dto.setTotalLeitores(usuarioRepository.count());

        dto.setEmprestimosAtivos((
                emprestimoRepository.findAll().stream()
                        .filter(e->e.getEmpTxStatus() == StatusEmprestimo.ATIVO)
                        .count()
        ));

        dto.setEmprestimosAtrasados(
                emprestimoRepository.countByEmpTxStatusAndEmpDtDevolucaoPrevistaBefore(
                        StatusEmprestimo.ATIVO,
                        LocalDate.now()
                )
        );

        return dto;
    }
}
