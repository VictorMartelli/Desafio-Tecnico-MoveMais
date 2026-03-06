package com.movemais.estoque.api.controller;

import com.movemais.estoque.api.dto.MovimentacaoRequest;
import com.movemais.estoque.api.dto.ProdutoRequest;
import com.movemais.estoque.api.dto.ProdutoResponse;
import com.movemais.estoque.application.usecases.CadastrarProdutoUseCase;
import com.movemais.estoque.application.usecases.ConsultarSaldoUseCase;
import com.movemais.estoque.application.usecases.ListarProdutosUseCase;
import com.movemais.estoque.application.usecases.RegistrarMovimentacaoUseCase;
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
    private final RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase;
    private final ConsultarSaldoUseCase consultarSaldoUseCase;

    public ProdutoController(CadastrarProdutoUseCase cadastrarProdutoUseCase,
                             ListarProdutosUseCase listarProdutosUseCase,
                             RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase,
                             ConsultarSaldoUseCase consultarSaldoUseCase) {
        this.cadastrarProdutoUseCase = cadastrarProdutoUseCase;
        this.listarProdutosUseCase = listarProdutosUseCase;
        this.registrarMovimentacaoUseCase = registrarMovimentacaoUseCase;
        this.consultarSaldoUseCase = consultarSaldoUseCase;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody ProdutoRequest request) {
        Produto novoProduto = cadastrarProdutoUseCase.executar(
            request.sku(),
            request.nome(),
            request.descricao(),
            request.estoqueMinimo()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(novoProduto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar(@RequestParam(required = false) Boolean ativo) {
        List<ProdutoResponse> produtos = listarProdutosUseCase.executar(ativo)
            .stream()
            .map(this::toResponse)
            .toList();

        return ResponseEntity.ok(produtos);
    }

    @PostMapping("/{id}/movimentacoes")
    public ResponseEntity<Void> movimentar(@PathVariable Long id,
                                           @Valid @RequestBody MovimentacaoRequest request) {

        registrarMovimentacaoUseCase.executar(
            id,
            request.quantidade(),
            request.tipo(),
            request.origem()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Integer> consultarSaldo(@PathVariable Long id) {
        Integer saldo = consultarSaldoUseCase.executar(id);
        return ResponseEntity.ok(saldo);
    }

    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
            produto.getId(),
            produto.getSku().getValor(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getEstoqueMinimo(),
            produto.getSaldo(),
            produto.isAtivo()
        );
    }
}
