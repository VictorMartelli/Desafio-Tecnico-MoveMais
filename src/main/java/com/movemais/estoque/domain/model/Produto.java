package com.movemais.estoque.domain.model;

import com.movemais.estoque.domain.valueobjects.Sku;
import java.io.Serializable;

/**
 * Entidade de Domínio representando um Produto.
 * Contém a lógica de negócio e garante que o saldo nunca fique negativo.
 */
public class Produto implements Serializable {
    
    private Long id;
    private Sku sku;
    private String nome;
    private String descricao;
    private Integer estoqueMinimo;
    private Integer saldo;
    private boolean ativo;

    // Construtor principal para criação de novos produtos
    public Produto(Sku sku, String nome, String descricao, Integer estoqueMinimo) {
        validarCampos(sku, nome, estoqueMinimo);
        this.sku = sku;
        this.nome = nome;
        this.descricao = descricao; // NOVO
        this.estoqueMinimo = estoqueMinimo;
        this.saldo = 0;
        this.ativo = true;
    }

    /**
     * Validações básicas de integridade do objeto.
     */
    private void validarCampos(Sku sku, String nome, Integer estoqueMinimo) {
        if (sku == null) throw new IllegalArgumentException("SKU é obrigatório.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (estoqueMinimo == null || estoqueMinimo < 0) {
            throw new IllegalArgumentException("Estoque mínimo deve ser maior ou igual a zero.");
        }
    }

    // --- Regras de Negócio (Movimentação) ---

    /**
     * Adiciona quantidade ao saldo atual.
     */
    public void adicionarEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de entrada deve ser maior que zero.");
        }
        this.saldo += quantidade;
    }

    /**
     * Remove quantidade do saldo atual.
     * REGRA: Impede que o saldo fique negativo lançando uma exceção de negócio.
     */
    public void removerEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de saída deve ser maior que zero.");
        }
        
        if (this.saldo < quantidade) {
            // Esta é a exceção que o Controller deve capturar para retornar 400 Bad Request
            throw new IllegalStateException("Saldo insuficiente! Operação negada. Saldo atual: " + this.saldo);
        }
        
        this.saldo -= quantidade;
    }

    // --- Métodos de Estado ---

    public void ativar() {
        this.ativo = true;
    }

    public void inativar() {
        this.ativo = false;
    }

    // --- Getters e Setters Técnicos (Uso para Persistência/Adapter) ---

    public Long getId() { return id; }
    
    public void setId(Long id) { this.id = id; }

    public Sku getSku() { return sku; }

    public String getNome() { return nome; }

    public Integer getEstoqueMinimo() { return estoqueMinimo; }

    public Integer getSaldo() { return saldo; }

    public void setSaldo(Integer saldo) { 
        if (saldo != null && saldo >= 0) {
            this.saldo = saldo; 
        }
    }

    public boolean isAtivo() { return ativo; }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}