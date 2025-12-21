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
    private String tipo; // ENTRADA, SAIDA, AJUSTE_POSITIVO, AJUSTE_NEGATIVO

    @Column(nullable = false)
    private String origem; // COMPRAS, VENDA, AJUSTE_MANUAL

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    /**
     * Construtor auxiliar atualizado para suportar o campo origem
     */
    public MovimentacaoEntity(Long produtoId, Integer quantidade, String tipo, String origem) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.origem = origem;
        this.dataHora = LocalDateTime.now();
    }
}