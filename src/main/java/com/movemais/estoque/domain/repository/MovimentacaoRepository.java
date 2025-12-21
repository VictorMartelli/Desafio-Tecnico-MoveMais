package com.movemais.estoque.domain.repository;

import com.movemais.estoque.domain.model.Movimentacao;
import java.util.List;

/**
 * Interface de repositório para o agregado de Movimentação.
 * Define o contrato de persistência conforme os princípios da Clean Architecture.
 */
public interface MovimentacaoRepository {

    /**
     * Salva um registro histórico de movimentação.
     * @param movimentacao Objeto de domínio a ser persistido.
     */
    void salvar(Movimentacao movimentacao);

    /**
     * Busca o histórico de movimentações de um produto específico.
     * @param produtoId Identificador único do produto.
     * @return Lista de movimentações encontradas.
     */
    List<Movimentacao> buscarPorProdutoId(Long produtoId);
}