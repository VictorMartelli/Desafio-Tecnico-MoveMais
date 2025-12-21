package com.movemais.estoque.domain.model;

import com.movemais.estoque.domain.valueobjects.Sku;
import java.io.Serializable;

/**
 * Entidade de Domínio representando um Produto.
 * Contém as regras de negócio essenciais e protege a integridade dos dados.
 */
public class Produto implements Serializable {
    
    private Long id;
    private Sku sku;
    private String nome;
    private Integer estoqueMinimo;
    private Integer saldo;
    private boolean ativo;

    // Construtor principal para criação de novos produtos (Cadastro)
    public Produto(Sku sku, String nome, Integer estoqueMinimo) {
        validarCampos(sku, nome, estoqueMinimo);
        this.sku = sku;
        this.nome = nome;
        this.estoqueMinimo = estoqueMinimo;
        this.saldo = 0; // Inicializa sempre com estoque zerado
        this.ativo = true; // Por padrão, o produto nasce ativo
    }

    /**
     * Valida as invariantes do domínio para impedir a criação de objetos inconsistentes.
     */
    private void validarCampos(Sku sku, String nome, Integer estoqueMinimo) {
        if (sku == null) throw new IllegalArgumentException("SKU é obrigatório.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (estoqueMinimo == null || estoqueMinimo < 0) {
            throw new IllegalArgumentException("Estoque mínimo deve ser maior ou igual a zero.");
        }
    }

    // --- Métodos de Comportamento (Regras de Negócio) ---

    public void ativar() {
        this.ativo = true;
    }

    public void inativar() {
        this.ativo = false;
    }

    /**
     * Incrementa o saldo atual do produto.
     * @param quantidade Valor a ser adicionado.
     */
    public void adicionarEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade para entrada deve ser positiva.");
        }
        this.saldo += quantidade;
    }

    /**
     * Decrementa o saldo atual do produto.
     * @param quantidade Valor a ser removido.
     */
    public void removerEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade para saída deve ser positiva.");
        }
        if (this.saldo < quantidade) {
            throw new IllegalStateException("Saldo insuficiente para realizar a saída.");
        }
        this.saldo -= quantidade;
    }

    // --- Getters e Setters Técnicos (Uso exclusivo do Repository/Adapter) ---

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
}