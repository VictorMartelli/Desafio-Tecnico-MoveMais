package com.movemais.estoque.domain.valueobjects;

import java.io.Serializable;
import java.util.Objects;

public class Sku implements Serializable {
    
    private static final String PATTERN = "^[A-Z0-9-]+$";

    private final String valor;

    public Sku(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU não pode ser vazio.");
        }
        
        // Remove espaços em branco antes de validar
        String valorLimpo = valor.trim();

        if (!valorLimpo.matches(PATTERN)) {
            throw new IllegalArgumentException("SKU inválido. Use apenas letras maiúsculas, números e hífens.");
        }
        
        this.valor = valorLimpo;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sku sku = (Sku) o;
        return Objects.equals(valor, sku.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}