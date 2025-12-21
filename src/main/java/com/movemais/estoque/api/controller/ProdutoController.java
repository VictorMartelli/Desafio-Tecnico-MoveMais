package com.movemais.estoque.api.controller;

import com.movemais.estoque.api.dto.ProdutoRequest;
import com.movemais.estoque.application.usecases.CadastrarProdutoUseCase;
import com.movemais.estoque.application.usecases.ListarProdutosUseCase;
import com.movemais.estoque.domain.model.Produto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final CadastrarProdutoUseCase cadastrarProdutoUseCase;
    private final ListarProdutosUseCase listarProdutosUseCase;

    public ProdutoController(
            CadastrarProdutoUseCase cadastrarProdutoUseCase,
            ListarProdutosUseCase listarProdutosUseCase) {
        this.cadastrarProdutoUseCase = cadastrarProdutoUseCase;
        this.listarProdutosUseCase = listarProdutosUseCase;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody @Valid ProdutoRequest request) {
        Produto produto = cadastrarProdutoUseCase.executar(
                request.sku(),
                request.nome(),
                request.estoqueMinimo()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar(@RequestParam(required = false) Boolean ativo) {
        // A lógica de decidir se filtra ou não está dentro do Use Case
        List<Produto> produtos = listarProdutosUseCase.executar(ativo);
        return ResponseEntity.ok(produtos);
    }
}