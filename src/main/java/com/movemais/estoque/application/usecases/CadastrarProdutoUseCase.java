package com.movemais.estoque.application.usecases;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import com.movemais.estoque.domain.valueobjects.Sku;
import org.springframework.stereotype.Service;

@Service
public class CadastrarProdutoUseCase {

    private final ProdutoRepository repository;

    public CadastrarProdutoUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    /**
     * Executa a lógica de cadastro de um novo produto.
     * Agora inclui a 'descricao' conforme a evolução do domínio.
     */
    public Produto executar(String sku, String nome, String descricao, Integer estoqueMinimo) {
        // O erro 'constructor is undefined' é resolvido aqui ao passar os 4 parâmetros
        Produto novoProduto = new Produto(
            new Sku(sku), 
            nome, 
            descricao, 
            estoqueMinimo
        );
        
        return repository.salvar(novoProduto);
    }
}