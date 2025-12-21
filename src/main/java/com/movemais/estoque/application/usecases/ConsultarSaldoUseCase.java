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

    /**
     * Executa a consulta de saldo garantindo que o produto existe no domínio.
     * @param produtoId ID do produto a ser consultado.
     * @return O saldo atualizado conforme as regras de negócio.
     */
    public Integer executar(Long produtoId) {
        return repository.buscarPorId(produtoId)
                .map(Produto::getSaldo)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + produtoId));
    }
}