package com.movemais.estoque.infrastructure.persistence;

import com.movemais.estoque.domain.model.Movimentacao;
import com.movemais.estoque.domain.repository.MovimentacaoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaMovimentacaoRepositoryAdapter implements MovimentacaoRepository {

    private final SpringDataMovimentacaoRepository repository;

    public JpaMovimentacaoRepositoryAdapter(SpringDataMovimentacaoRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("null")
    @Override
    public void salvar(Movimentacao domain) {
        // Converte o objeto de Domínio para a Entidade JPA antes de salvar
        MovimentacaoEntity entity = toEntity(domain);
        repository.save(entity);
    }

    @Override
    public List<Movimentacao> buscarPorProdutoId(Long produtoId) {
        // Busca as entidades e converte de volta para o modelo de Domínio
        return repository.findByProdutoId(produtoId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Mapeador: Domínio -> Entidade JPA
     */
    private MovimentacaoEntity toEntity(Movimentacao domain) {
        return new MovimentacaoEntity(
            domain.getProdutoId(),
            domain.getQuantidade(),
            domain.getTipo(),
            domain.getOrigem()
        );
    }

    /**
     * Mapeador: Entidade JPA -> Domínio
     */
    private Movimentacao toDomain(MovimentacaoEntity entity) {
        Movimentacao domain = new Movimentacao(
            entity.getProdutoId(),
            entity.getQuantidade(),
            entity.getTipo(),
            entity.getOrigem()
        );
        domain.setId(entity.getId()); // Preserva o ID gerado pelo banco
        // Note: Se sua entidade tiver dataHora, você pode setar aqui também via setter no domínio
        return domain;
    }
}