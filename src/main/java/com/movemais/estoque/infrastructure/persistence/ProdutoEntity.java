package com.movemais.estoque.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produtos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProdutoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(name = "estoque_minimo")
    private Integer estoqueMinimo;

    private boolean ativo;
}
