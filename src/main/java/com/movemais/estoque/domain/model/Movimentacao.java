package com.movemais.estoque.domain.model;

import java.time.LocalDateTime;

public class Movimentacao {
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private String tipo; // ENTRADA ou SAIDA
    private LocalDateTime dataHora;

    public Movimentacao(Long produtoId, Integer quantidade, String tipo) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.dataHora = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProdutoId() { return produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public String getTipo() { return tipo; }
    public LocalDateTime getDataHora() { return dataHora; }
}