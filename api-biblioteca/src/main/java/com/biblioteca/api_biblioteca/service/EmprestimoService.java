package com.biblioteca.api_biblioteca.service;

import com.biblioteca.api_biblioteca.dto.EmprestimoDTO;
import com.biblioteca.api_biblioteca.model.Emprestimo;
import com.biblioteca.api_biblioteca.model.Livro;
import com.biblioteca.api_biblioteca.model.StatusEmprestimo;
import com.biblioteca.api_biblioteca.model.Usuario;
import com.biblioteca.api_biblioteca.repository.EmprestimoRepository;
import com.biblioteca.api_biblioteca.repository.LivroRepository;
import com.biblioteca.api_biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    public Emprestimo salvar(EmprestimoDTO dto){
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado!"));
        Livro livro = livroRepository.findById(dto.getIdLivro())
                .orElseThrow(()-> new RuntimeException("Livro não encontrado"));

        if(emprestimoRepository.existsByLivNrAndEmpTxStatus(livro, StatusEmprestimo.ATIVO)){
            throw new RuntimeException("Este livro já está emprestado e indisponível no momento");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuNr(usuario);
        emprestimo.setLivNr(livro);
        emprestimo.setEmpDtEmprestimo(LocalDate.now());
        emprestimo.setEmpDtDevolucaoPrevista(LocalDate.now().plusDays(7));
        emprestimo.setEmpTxStatus(StatusEmprestimo.ATIVO);

        return emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> listarTodos(){
        return emprestimoRepository.findAll();
    }

    public void realizarDevolucao(Long idEmprestimo){
        Emprestimo emprestimo = emprestimoRepository.findById(idEmprestimo)
                .orElseThrow(()-> new RuntimeException("Emprestimo não encontrado!"));

        if(emprestimo.getEmpTxStatus() == StatusEmprestimo.DEVOLVIDO){
            throw new RuntimeException("Este livro já foi devolvido!");
        }

        emprestimo.setEmpDtDevolucaoReal(LocalDate.now());
        emprestimo.setEmpTxStatus(StatusEmprestimo.DEVOLVIDO);

        emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> listarPorUsuario(Usuario usuario){
        return emprestimoRepository.findByUsuNr(usuario);
    }

    public void renovar(Long idEmprestimo){
        Emprestimo emprestimo = emprestimoRepository.findById(idEmprestimo)
                .orElseThrow(()-> new RuntimeException(("Emprestimo não encontrado!")));
        if(emprestimo.getEmpTxStatus() != StatusEmprestimo.ATIVO){
            throw new RuntimeException("Não é possível renovar um empréstimo devolvido.");
        }
        if(emprestimo.getEmpDtDevolucaoPrevista().isBefore(LocalDate.now())){
            throw new RuntimeException("Livro em atraso! Devolva o livro para regularizar.");
        }
        int renovacoes = emprestimo.getEmpNrRenovacoes() == null ? 0 : emprestimo.getEmpNrRenovacoes();
        if(renovacoes >= 3){
            throw new RuntimeException("Limite de 3 renovações atingido. É necessário devolver.");
        }

        emprestimo.setEmpDtDevolucaoPrevista(emprestimo.getEmpDtDevolucaoPrevista().plusDays(7));
        emprestimo.setEmpNrRenovacoes(renovacoes+1);
        emprestimoRepository.save(emprestimo);
    }
}
