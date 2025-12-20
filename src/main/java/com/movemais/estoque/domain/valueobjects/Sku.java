package com.movemais.estoque.domain.valueobjects;
/**
 * Representa o código único do produto (Stock Keeping Unit).
 * Como Value Object, ele garante sua própria validade.
 */
public record Sku(String value) {
    public Sku {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("O SKU não pode ser vazio.");
        }
        // Regra de negócio: SKU deve ter apenas letras e números
        if (value.matches("^[A-Z0-9-]+$")) {
            throw new IllegalArgumentException("SKU inválido. Use apenas letras maiúsculas, números e hífens");
        }
    }
}
