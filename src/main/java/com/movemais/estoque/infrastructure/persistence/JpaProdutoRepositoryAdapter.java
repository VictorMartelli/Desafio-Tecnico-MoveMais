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
        return repository.findByAtivo(ativo).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorSku(Sku sku) {
        return repository.existsBySku(sku.getValor());
    }

    /**
     * Converte o objeto de Domínio para a Entidade JPA.
     * Importante: Passamos o saldo para garantir que ele seja persistido.
     */
    private ProdutoEntity toEntity(Produto p) {
        return new ProdutoEntity(
            p.getId(),
            p.getSku().getValor(),
            p.getNome(),
            null, // Preço (pode ser expandido futuramente)
            p.getEstoqueMinimo(),
            p.isAtivo(),
            p.getSaldo() // MAPEADO: Saldo do domínio indo para o banco
        );
    }

    /**
     * Converte a Entidade JPA para o objeto de Domínio.
     * Importante: Reconstruímos o estado do Produto com o ID e Saldo vindos do banco.
     */
    private Produto toDomain(ProdutoEntity e) {
        Produto produto = new Produto(
            new Sku(Objects.requireNonNull(e.getSku())),
            Objects.requireNonNull(e.getNome()),
            e.getEstoqueMinimo()
        );

        // MAPEADO: ID e Saldo do banco voltando para o domínio
        produto.setId(e.getId());
        produto.setSaldo(e.getSaldo()); 

        if (e.isAtivo()) {
            produto.ativar();
        } else {
            produto.inativar();
        }

        return produto;
    }
}