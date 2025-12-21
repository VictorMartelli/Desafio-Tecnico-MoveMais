package com.movemais.estoque.application.usecases;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.model.Movimentacao;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import com.movemais.estoque.domain.repository.MovimentacaoRepository; // Agora usa o contrato do domínio
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarMovimentacaoUseCase {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public RegistrarMovimentacaoUseCase(
            ProdutoRepository produtoRepository, 
            MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Transactional
    public void executar(Long produtoId, Integer quantidade, String tipo, String origem) {
        // 1. Busca o produto via interface de repositório de domínio
        Produto produto = produtoRepository.buscarPorId(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + produtoId));

        // 2. Orquestração da Regra de Negócio de integridade de saldo
        if ("ENTRADA".equalsIgnoreCase(tipo) || "AJUSTE_POSITIVO".equalsIgnoreCase(tipo)) {
            produto.adicionarEstoque(quantidade);
        } 
        else if ("SAIDA".equalsIgnoreCase(tipo) || "AJUSTE_NEGATIVO".equalsIgnoreCase(tipo)) {
            // Delega a validação de saldo negativo para o Domínio
            produto.removerEstoque(quantidade);
        } else {
            throw new IllegalArgumentException("Tipo de movimento inválido: " + tipo);
        }

        // 3. Persiste a atualização do saldo através do adaptador
        produtoRepository.salvar(produto);

        // 4. Cria o objeto de Domínio e registra no histórico via interface
        Movimentacao movimentacao = new Movimentacao(
            produtoId, 
            quantidade, 
            tipo.toUpperCase(), 
            origem.toUpperCase()
        );
        
        movimentacaoRepository.salvar(movimentacao);
    }
}