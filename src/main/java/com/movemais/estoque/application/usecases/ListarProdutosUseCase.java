package com.movemais.estoque.application.usecases;

import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListarProdutosUseCase {

    private final ProdutoRepository repository;

    public ListarProdutosUseCase(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> executar(Boolean ativo) {
        if (ativo != null) {
            return repository.listarPorStatus(ativo);
        }
        return repository.listarTodos();
    }
}