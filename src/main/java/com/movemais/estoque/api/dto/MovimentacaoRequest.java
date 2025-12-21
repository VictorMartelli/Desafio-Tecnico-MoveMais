package com.movemais.estoque.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovimentacaoRequest(
    @NotNull(message = "O ID do produto é obrigatório")
    Long produtoId,

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade mínima deve ser 1")
    Integer quantidade,

    @NotBlank(message = "O tipo (ENTRADA/SAIDA) é obrigatório")
    String tipo
) {}