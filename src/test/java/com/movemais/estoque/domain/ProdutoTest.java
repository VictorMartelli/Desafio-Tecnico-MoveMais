package com.movemais.estoque.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.valueobjects.Sku;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    @DisplayName("Deve garantir que o saldo não fique negativo")
    void deveValidarSaldoNegativo() {
        Produto produto = new Produto(new Sku("TEST-123"), "Produto Teste", null, 5);
        produto.adicionarEstoque(10);
        
        assertThrows(IllegalStateException.class, () -> {
            produto.removerEstoque(11);
        });
    }
}