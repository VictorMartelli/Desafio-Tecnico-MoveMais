package com.movemais.estoque.application.usecases;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarMovimentacaoUseCase {

    private final ProdutoRepository repository;

    public RegistrarMovimentacaoUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void executar(Long produtoId, Integer quantidade, String tipo) {
        Produto produto = repository.buscarPorId(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado ID: " + produtoId));

        if ("ENTRADA".equalsIgnoreCase(tipo)) {
            produto.adicionarEstoque(quantidade);
        } else if ("SAIDA".equalsIgnoreCase(tipo)) {
            // A regra de negócio de estoque < 0 está dentro deste método do domínio
            produto.removerEstoque(quantidade); 
        } else {
            throw new IllegalArgumentException("Tipo de movimentação inválido. Use ENTRADA ou SAIDA.");
        }

        repository.salvar(produto);
    }
}