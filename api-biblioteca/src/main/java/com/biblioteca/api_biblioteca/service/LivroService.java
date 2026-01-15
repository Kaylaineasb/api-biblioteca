package com.biblioteca.api_biblioteca.service;

import com.biblioteca.api_biblioteca.model.Livro;
import com.biblioteca.api_biblioteca.model.StatusEmprestimo;
import com.biblioteca.api_biblioteca.repository.EmprestimoRepository;
import com.biblioteca.api_biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public List<Livro> listarTodos() {
        List<Livro> livros = livroRepository.findAll();

        for (Livro livro : livros) {
            boolean estaEmprestado = emprestimoRepository.existsByLivNrAndEmpTxStatus(
                    livro,
                    StatusEmprestimo.ATIVO
            );
            livro.setDisponivel(!estaEmprestado);
        }
        return livros;
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }
}
