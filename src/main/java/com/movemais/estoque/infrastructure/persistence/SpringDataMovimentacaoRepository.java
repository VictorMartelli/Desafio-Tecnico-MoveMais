package com.movemais.estoque.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpringDataMovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {
    
    // Método para buscar todas as movimentações de um produto específico
    List<MovimentacaoEntity> findByProdutoId(Long produtoId);
}