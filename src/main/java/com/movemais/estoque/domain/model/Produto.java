package com.movemais.estoque.domain.model;

import com.movemais.estoque.domain.valueobjects.Sku;
import java.io.Serializable;

public class Produto implements Serializable {
    
    private Long id;
    private Sku sku;
    private String nome;
    private Integer estoqueMinimo;
    private boolean ativo;

    // Construtor principal para novos produtos (Cadastro)
    public Produto(Sku sku, String nome, Integer estoqueMinimo) {
        validarCampos(sku, nome, estoqueMinimo);
        this.sku = sku;
        this.nome = nome;
        this.estoqueMinimo = estoqueMinimo;
        this.ativo = true; // Todo produto nasce ativo por padrão
    }

    // Regras de validação de domínio
    private void validarCampos(Sku sku, String nome, Integer estoqueMinimo) {
        if (sku == null) throw new IllegalArgumentException("SKU é obrigatório.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (estoqueMinimo == null || estoqueMinimo < 0) {
            throw new IllegalArgumentException("Estoque mínimo deve ser maior ou igual a zero.");
        }
    }

    // Métodos para o Adapter sincronizar o estado do Banco de Dados
    public void setId(Long id) {
        this.id = id;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void inativar() {
        this.ativo = false;
    }

    // Getters
    public Long getId() { return id; }
    public Sku getSku() { return sku; }
    public String getNome() { return nome; }
    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public boolean isAtivo() { return ativo; }
}