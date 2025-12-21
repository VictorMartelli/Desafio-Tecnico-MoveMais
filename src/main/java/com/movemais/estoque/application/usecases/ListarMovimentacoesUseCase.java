package com.movemais.estoque.application.usecases;

import com.movemais.estoque.infrastructure.persistence.MovimentacaoEntity;
import com.movemais.estoque.infrastructure.persistence.SpringDataMovimentacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarMovimentacoesUseCase {

    private final SpringDataMovimentacaoRepository repository;

    public ListarMovimentacoesUseCase(SpringDataMovimentacaoRepository repository) {
        this.repository = repository;
    }

    public List<MovimentacaoEntity> executar(Long produtoId) {
        // Busca todas as linhas da tabela 'movimentacoes' filtrando pelo ID do produto
        return repository.findByProdutoId(produtoId);
    }
}