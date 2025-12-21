package com.movemais.estoque.infrastructure.persistence;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import com.movemais.estoque.domain.valueobjects.Sku;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaProdutoRepositoryAdapter implements ProdutoRepository {

    private final SpringDataProdutoRepository repository;

    public JpaProdutoRepositoryAdapter(SpringDataProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Produto salvar(Produto produto) {
        ProdutoEntity entity = toEntity(produto);
        // O salvamento retorna a entidade persistida, garantindo o ID gerado
        @SuppressWarnings("null")
        ProdutoEntity savedEntity = repository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        if (id == null) return Optional.empty();
        // O uso do .map() lida com o Optional de forma segura para o compilador
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Produto> buscarPorSku(Sku sku) {
        if (sku == null || sku.getValor() == null) return Optional.empty();
        return repository.findBySku(sku.getValor()).map(this::toDomain);
    }

    @Override
    public List<Produto> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Produto> listarPorStatus(boolean ativo) {
        return repository.findAll().stream()
                .filter(p -> p.isAtivo() == ativo)
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorSku(Sku sku) {
        if (sku == null || sku.getValor() == null) return false;
        return repository.findBySku(sku.getValor()).isPresent();
    }

    /**
     * Converte o Domínio para Entidade JPA.
     * Resolve os erros de construtor indefinido.
     */
    private ProdutoEntity toEntity(Produto domain) {
        return new ProdutoEntity(
            domain.getId(),
            domain.getSku().getValor(),
            domain.getNome(),
            domain.getDescricao(), 
            domain.getEstoqueMinimo(),
            domain.getSaldo(),
            domain.isAtivo()
        );
    }

    /**
     * Converte a Entidade JPA para Domínio.
     * Reconstrói o objeto com a nova estrutura de dados.
     */
    private Produto toDomain(ProdutoEntity entity) {
        // Blindagem contra entidade nula na conversão
        if (entity == null) throw new IllegalArgumentException("Entidade não pode ser nula");

        Produto produto = new Produto(
            new Sku(entity.getSku()), 
            entity.getNome(), 
            entity.getDescricao(), 
            entity.getEstoqueMinimo()
        );
        
        produto.setId(entity.getId());
        produto.setSaldo(entity.getSaldo());
        
        if (!entity.isAtivo()) {
            produto.inativar();
        }
        
        return produto;
    }
}