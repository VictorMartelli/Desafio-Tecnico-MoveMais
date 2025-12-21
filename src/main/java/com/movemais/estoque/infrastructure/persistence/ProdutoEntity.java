package com.movemais.estoque.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade de Infraestrutura (JPA) para persistência no banco de dados.
 * Esta classe é um "espelho" da tabela no H2 e não deve conter lógica de negócio.
 */
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

    private Double preco; // Campo opcional para futuras expansões

    @Column(name = "estoque_minimo")
    private Integer estoqueMinimo;

    @Column(nullable = false)
    private Integer saldo = 0; // Inicializado com zero no banco de dados

    private boolean ativo = true;

    /**
     * Construtor auxiliar para facilitar o mapeamento no Adapter.
     */
    public ProdutoEntity(Long id, String sku, String nome, Double preco, Integer estoqueMinimo, boolean ativo, Integer saldo) {
        this.id = id;
        this.sku = sku;
        this.nome = nome;
        this.preco = preco;
        this.estoqueMinimo = estoqueMinimo;
        this.ativo = ativo;
        this.saldo = saldo != null ? saldo : 0;
    }
}