package com.movemais.estoque.domain.repository;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.valueobjects.Sku;
import java.util.Optional;
import java.util.List;

/**
 * Interface de domínio para operações de persistência de Produto.
 * Seguindo a Clean Architecture, esta é uma 'Porta' (Port).
 */
public interface ProdutoRepository {
    Produto salvar(Produto produto);
    Optional<Produto> buscarPorSku(Sku sku);
    Optional<Produto> buscarPorId(Long id);
    List<Produto> listarTodos();
    boolean existePorSku(Sku sku);
}

/*Se o Spring Data JPA mudar ou se usarmos JDBC puro, as regras de negócio que chamam essa interface não quebram 
 */

/*Podemos também criar um "mock" desta interface para testar a lógica de negócio sem precisar subir um banco real
 */