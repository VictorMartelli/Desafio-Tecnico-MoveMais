package com.movemais.estoque.application.usecases;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import com.movemais.estoque.domain.valueobjects.Sku;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Caso de Uso responsável pelo cadastro de novos produtos.
 * Orquestra a validação de SKU único e a persistência inicial.
 */
@Service
public class CadastrarProdutoUseCase {

    private final ProdutoRepository produtoRepository;

    public CadastrarProdutoUseCase(ProdutoRepository produtoRepository) {
        this.produtoRepository = Objects.requireNonNull(produtoRepository);
    }

    @Transactional
    public Produto executar(String skuRaw, String nome, Integer estoqueMinimo) {
        Sku sku = new Sku(skuRaw);

        // Regra de Negócio: Não podem existir dois produtos com o mesmo SKU
        if (produtoRepository.existePorSku(sku)) {
            throw new IllegalArgumentException("Já existe um produto cadastrado com o SKU: " + skuRaw);
        }

        // Criando a Entidade Rica (as validações internas de Produto são disparadas aqui)
        Produto novoProduto = new Produto(sku, nome, estoqueMinimo);
        
        // Persistindo através do Adaptador de Infraestrutura
        return produtoRepository.salvar(novoProduto);
    }
}