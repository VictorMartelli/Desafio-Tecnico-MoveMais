package com.movemais.estoque.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produtos")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String nome;

    // Garanta que este campo existe aqui para o Hibernate criar a coluna no H2
    private String descricao; 

    @Column(name = "estoque_minimo")
    private Integer estoqueMinimo;

    private Integer saldo;

    private boolean ativo;

    /**
     * Construtor auxiliar atualizado para incluir a descrição.
     */
    public ProdutoEntity(String sku, String nome, String descricao, Integer estoqueMinimo, Integer saldo, boolean ativo) {
        this.sku = sku;
        this.nome = nome;
        this.descricao = descricao;
        this.estoqueMinimo = estoqueMinimo;
        this.saldo = saldo;
        this.ativo = ativo;
    }
}