package com.movemais.estoque.infrastructure.persistence;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import com.movemais.estoque.domain.valueobjects.Sku;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JpaProdutoRepositoryAdapter implements ProdutoRepository {

    private final SpringDataProdutoRepository repository;

    public JpaProdutoRepositoryAdapter(SpringDataProdutoRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("null")
    @Override
    public Produto salvar(Produto produto) {
        ProdutoEntity entity = toEntity(produto);
        ProdutoEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Produto> buscarPorSku(Sku sku) {
        return repository.findBySku(sku.value()).map(this::toDomain);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        // Tratar o id como não nulo antes da busca
        return repository.findById(Objects.requireNonNull(id)).map(this::toDomain);
    }

    @Override
    public List<Produto> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorSku(Sku sku) {
        return repository.existsBySku(sku.value());
    }

    private ProdutoEntity toEntity(Produto p) {
        return new ProdutoEntity(
            null, // ID gerado pelo banco
            p.getSku().value(), 
            p.getNome(), 
            null, 
            p.getEstoqueMinimo(), 
            p.isAtivo()
        );
    }

    private Produto toDomain(ProdutoEntity e) {
        // requireNonNull nos campos obrigatórios vindos da Entity
        return new Produto(
            new Sku(Objects.requireNonNull(e.getSku())), 
            Objects.requireNonNull(e.getNome()), 
            e.getEstoqueMinimo()
        );
    }
}