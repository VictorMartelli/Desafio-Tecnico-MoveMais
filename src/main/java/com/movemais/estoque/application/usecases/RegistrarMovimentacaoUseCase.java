package com.movemais.estoque.application.usecases;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import com.movemais.estoque.infrastructure.persistence.MovimentacaoEntity;
import com.movemais.estoque.infrastructure.persistence.SpringDataMovimentacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarMovimentacaoUseCase {

    private final ProdutoRepository produtoRepository;
    private final SpringDataMovimentacaoRepository movimentacaoRepository;

    public RegistrarMovimentacaoUseCase(
            ProdutoRepository produtoRepository, 
            SpringDataMovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Transactional
    public void executar(Long produtoId, Integer quantidade, String tipo) {
        // 1. Busca o produto e valida existência
        Produto produto = produtoRepository.buscarPorId(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado ID: " + produtoId));

        // 2. Aplica a regra de negócio no domínio (Entrada ou Saída)
        if ("ENTRADA".equalsIgnoreCase(tipo)) {
            produto.adicionarEstoque(quantidade);
        } else if ("SAIDA".equalsIgnoreCase(tipo)) {
            produto.removerEstoque(quantidade);
        } else {
            throw new IllegalArgumentException("Tipo de movimentação inválido: " + tipo);
        }

        // 3. Salva a alteração do saldo no produto
        produtoRepository.salvar(produto);

        // 4. NOVO: Salva o registro no histórico de movimentações
        MovimentacaoEntity historico = new MovimentacaoEntity(produtoId, quantidade, tipo.toUpperCase());
        movimentacaoRepository.save(historico);
    }
}