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
    public void executar(Long produtoId, Integer quantidade, String tipo, String origem) {
        // 1. Busca el producto y valida su existencia
        Produto produto = produtoRepository.buscarPorId(produtoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + produtoId));

        // 2. Lógica de Negocio Expandida:
        // Entradas y Ajustes Positivos suman al saldo
        if ("ENTRADA".equalsIgnoreCase(tipo) || "AJUSTE_POSITIVO".equalsIgnoreCase(tipo)) {
            produto.adicionarEstoque(quantidade);
        } 
        // Salidas y Ajustes Negativos restan del saldo (con validación de saldo insuficiente)
        else if ("SAIDA".equalsIgnoreCase(tipo) || "AJUSTE_NEGATIVO".equalsIgnoreCase(tipo)) {
            produto.removerEstoque(quantidade);
        } else {
            throw new IllegalArgumentException("Tipo de movimiento inválido: " + tipo);
        }

        // 3. Guarda la actualización del saldo del producto
        produtoRepository.salvar(produto);

        // 4. Guarda el registro en el historial con el nuevo campo 'origem'
        MovimentacaoEntity historico = new MovimentacaoEntity(
            produtoId, 
            quantidade, 
            tipo.toUpperCase(), 
            origem.toUpperCase()
        );
        
        movimentacaoRepository.save(historico);
    }
}