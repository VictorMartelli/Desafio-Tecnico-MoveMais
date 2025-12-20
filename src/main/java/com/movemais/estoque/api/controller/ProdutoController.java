package com.movemais.estoque.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movemais.estoque.api.dto.ProdutoRequest;
import com.movemais.estoque.application.usecases.CadastrarProdutoUseCase;
import com.movemais.estoque.domain.model.Produto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    private final CadastrarProdutoUseCase cadastrarProdutoUseCase;

    //Construtor
    public ProdutoController(CadastrarProdutoUseCase cadastrarProdutoUseCase) {
        this.cadastrarProdutoUseCase = cadastrarProdutoUseCase;
    }

    public ResponseEntity<Produto> cadastrar(@RequestBody @Valid ProdutoRequest request) {
        Produto produto = cadastrarProdutoUseCase.executar(
            request.sku(),
            request.nome(),
            request.estoqueMinimo()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }
}
