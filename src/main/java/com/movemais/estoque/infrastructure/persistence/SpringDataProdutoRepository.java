package com.movemais.estoque.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface SpringDataProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    /**
     * Busca uma entidade de produto pelo seu código SKU.
     */
    Optional<ProdutoEntity> findBySku(String sku);

    /**
     * Verifica se já existe um produto cadastrado com o SKU informado.
     */
    boolean existsBySku(String sku);

    /**
     * Busca todos os produtos filtrando pelo status (ativo ou inativo).
     * O Spring Data JPA gera a query automaticamente com base no nome do método.
     */
    List<ProdutoEntity> findByAtivo(boolean ativo);
}