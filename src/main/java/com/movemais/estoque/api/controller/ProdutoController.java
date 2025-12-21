package com.movemais.estoque.api.controller;

import com.movemais.estoque.api.dto.ProdutoRequest;
import com.movemais.estoque.application.usecases.CadastrarProdutoUseCase;
import com.movemais.estoque.application.usecases.ListarProdutosUseCase;
import com.movemais.estoque.application.usecases.ConsultarSaldoUseCase;
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
    private final ConsultarSaldoUseCase consultarSaldoUseCase;

    public ProdutoController(
            CadastrarProdutoUseCase cadastrarProdutoUseCase,
            ListarProdutosUseCase listarProdutosUseCase,
            ConsultarSaldoUseCase consultarSaldoUseCase) {
        this.cadastrarProdutoUseCase = cadastrarProdutoUseCase;
        this.listarProdutosUseCase = listarProdutosUseCase;
        this.consultarSaldoUseCase = consultarSaldoUseCase;
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
        List<Produto> produtos = listarProdutosUseCase.executar(ativo);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Endpoint para consulta de saldo atual do produto.
     * GET /api/produtos/{id}/saldo
     */
    @GetMapping("/{id}/saldo")
    public ResponseEntity<Integer> consultarSaldo(@PathVariable Long id) {
        Integer saldo = consultarSaldoUseCase.executar(id);
        return ResponseEntity.ok(saldo);
    }
}