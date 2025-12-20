package com.movemais.estoque.domain.model;

import com.movemais.estoque.domain.valueobjects.Sku;
import java.util.Objects;

public class Produto {
    private Long id;
    private Sku sku;
    private String nome;
    private String descricao;
    private Integer estoqueMinimo;
    private boolean ativo;

    // Construtor garante a integridade do objeto desde o nascimento
    public Produto(Sku sku, String nome, Integer estoqueMinimo) {
        this.sku = Objects.requireNonNull(sku, "SKU é obrigatório");
        this.nome = Objects.requireNonNull(nome, "Nome é obrigatório");
        this.estoqueMinimo = (estoqueMinimo != null && estoqueMinimo >= 0) ? estoqueMinimo : 0;
        this.ativo = true; // Todo produto nasce ativo conforme o requisito
    }

    public void desativar() {
        this.ativo = false;
    }

    // Getters
    public Sku getSku() { return sku; }
    public String getNome() { return nome; }
    public boolean isAtivo() { return ativo; }
    public Integer getEstoqueMinimo() { return estoqueMinimo; }
}