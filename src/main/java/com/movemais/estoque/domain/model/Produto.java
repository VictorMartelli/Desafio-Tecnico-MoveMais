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

    /**
     * Construtor padrão necessário para frameworks de serialização (Jackson/JSON).
     */
    public Produto() {
    }

    /**
     * Construtor principal para criação de novos produtos via Use Cases.
     */
    public Produto(Sku sku, String nome, String descricao, Integer estoqueMinimo) {
        validarCampos(sku, nome, estoqueMinimo);
        this.sku = sku;
        this.nome = nome;
        this.descricao = descricao;
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
            throw new IllegalStateException("Saldo insuficiente! Operação negada. Saldo atual: " + this.saldo);
        }
        
        this.saldo -= quantidade;
    }

    /**
     * Verifica se o produto está abaixo do estoque de segurança.
     */
    public boolean estaAbaixoDoMinimo() {
        return this.saldo < this.estoqueMinimo;
    }

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Sku getSku() { return sku; }
    public void setSku(Sku sku) { this.sku = sku; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Integer estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }

    public Integer getSaldo() { return saldo; }
    public void setSaldo(Integer saldo) { 
        if (saldo != null && saldo >= 0) {
            this.saldo = saldo; 
        }
    }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public void ativar() {
        this.ativo = true;
    }

    public void inativar() {
        this.ativo = false;
    }
}