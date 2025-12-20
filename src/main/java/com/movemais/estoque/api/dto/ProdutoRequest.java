package com.movemais.estoque.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ProdutoRequest(
    @NotBlank(message = "O SKU é obrigatório")
    String sku,
    
    @NotBlank(message = "O nome é obrigatório")
    String nome,
    
    @PositiveOrZero(message = "O estoque mínimo não pode ser negativo")
    Integer estoqueMinimo
) {}