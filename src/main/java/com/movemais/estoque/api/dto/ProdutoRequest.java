package com.movemais.estoque.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para recebimento de dados na criação de um produto.
 * Inclui o novo campo 'descricao' conforme a evolução do domínio.
 */
public record ProdutoRequest(
    @NotBlank(message = "O SKU é obrigatório.")
    String sku,

    @NotBlank(message = "O nome é obrigatório.")
    String nome,

    @NotBlank(message = "A descrição é obrigatória.")
    String descricao,

    @NotNull(message = "O estoque mínimo é obrigatório.")
    @Min(value = 0, message = "O estoque mínimo não pode ser negativo.")
    Integer estoqueMinimo
) {}