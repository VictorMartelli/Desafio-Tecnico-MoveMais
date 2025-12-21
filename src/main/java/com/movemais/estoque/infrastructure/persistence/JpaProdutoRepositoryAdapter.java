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
        return repository.findBySku(sku.getValor()).map(this::toDomain);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return repository.findById(Objects.requireNonNull(id)).map(this::toDomain);
    }

    @Override
    public List<Produto> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Produto> listarPorStatus(boolean ativo) {
        // IMPLEMENTADO: Chama o Spring Data e converte para domínio
        return repository.findByAtivo(ativo).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorSku(Sku sku) {
        return repository.existsBySku(sku.getValor());
    }

    private ProdutoEntity toEntity(Produto p) {
        return new ProdutoEntity(
            p.getId(), // Alterado: Passa o ID do domínio para a Entity (evita duplicidade)
            p.getSku().getValor(), 
            p.getNome(), 
            null, 
            p.getEstoqueMinimo(), 
            p.isAtivo()
        );
    }

    private Produto toDomain(ProdutoEntity e) {
        // Criamos a instância de domínio
        Produto produto = new Produto(
            new Sku(Objects.requireNonNull(e.getSku())), 
            Objects.requireNonNull(e.getNome()), 
            e.getEstoqueMinimo()
        );

        // IMPORTANTE: Reatribuímos o ID e o status Ativo vindo do Banco
        produto.setId(e.getId());
        if (e.isAtivo()) {
            produto.ativar();
        } else {
            produto.inativar();
        }

        return produto;
    }
}