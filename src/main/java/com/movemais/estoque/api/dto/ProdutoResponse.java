package com.movemais.estoque.api.dto;

/**
 * DTO de saída para exposição de dados de produto na API.
 */
public record ProdutoResponse(
    Long id,
    String sku,
    String nome,
    String descricao,
    Integer estoqueMinimo,
    Integer saldo,
    boolean ativo
) {}
