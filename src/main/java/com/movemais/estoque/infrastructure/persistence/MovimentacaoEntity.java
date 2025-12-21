package com.movemais.estoque.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class MovimentacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "produto_id", nullable = false)
    private Long produtoId;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private String tipo; // "ENTRADA" ou "SAIDA"

    @Column(name = "data_ora", nullable = false)
    private LocalDateTime dataHora;

    // Construtor auxiliar para facilitar a criação no Adapter
    public MovimentacaoEntity(Long produtoId, Integer quantidade, String tipo) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.dataHora = LocalDateTime.now();
    }
}