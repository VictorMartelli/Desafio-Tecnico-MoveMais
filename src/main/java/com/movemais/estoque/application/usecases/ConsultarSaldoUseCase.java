package com.movemais.estoque.application.usecases;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsultarSaldoUseCase {

    private final ProdutoRepository repository;

    public ConsultarSaldoUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Integer executar(Long id) {
        return repository.buscarPorId(id)
                .map(Produto::getSaldo) // Assume que vamos adicionar o campo saldo no Produto
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
    }
}