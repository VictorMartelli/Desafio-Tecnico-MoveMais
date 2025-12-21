package com.movemais.estoque.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para registro de movimentações de estoque.
 * Agora suporta novos tipos de ajuste e o campo de origem para auditoria.
 */
public record MovimentacaoRequest(
    @NotNull(message = "A quantidade é obrigatória.")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    Integer quantidade,

    @NotBlank(message = "O tipo de movimentação é obrigatório.")
    @Pattern(
        regexp = "ENTRADA|SAIDA|AJUSTE_POSITIVO|AJUSTE_NEGATIVO", 
        message = "Tipo inválido. Use: ENTRADA, SAIDA, AJUSTE_POSITIVO ou AJUSTE_NEGATIVO"
    )
    String tipo,

    @NotBlank(message = "A origem é obrigatória.") // Novo campo
    @Pattern(
        regexp = "COMPRAS|VENDA|AJUSTE_MANUAL", 
        message = "Origem inválida. Use: COMPRAS, VENDA ou AJUSTE_MANUAL"
    )
    String origem
) {}