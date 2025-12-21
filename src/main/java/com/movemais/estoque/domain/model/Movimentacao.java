package com.movemais.estoque.domain.model;

import java.time.LocalDateTime;

public class Movimentacao {
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private String tipo;
    private String origem; // Este é o campo que o VS Code sinalizou
    private LocalDateTime dataHora;

    public Movimentacao(Long produtoId, Integer quantidade, String tipo, String origem) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.origem = origem; // ATRIBUIÇÃO: Resolve o aviso de que o campo não é usado
        this.dataHora = LocalDateTime.now();
    }

    // GETTERS - Necessários para que o campo seja lido pelo resto do sistema
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProdutoId() { return produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public String getTipo() { return tipo; }
    
    public String getOrigem() { // GETTER: Permite o uso do campo
        return origem;
    }
    
    public LocalDateTime getDataHora() { return dataHora; }
}