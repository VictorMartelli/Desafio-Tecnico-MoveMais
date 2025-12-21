package com.movemais.estoque.api.controller;

import com.movemais.estoque.application.usecases.ConsultarSaldoUseCase;
import com.movemais.estoque.application.usecases.RegistrarMovimentacaoUseCase;
import com.movemais.estoque.domain.model.Produto;
import com.movemais.estoque.domain.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase;
    private final ConsultarSaldoUseCase consultarSaldoUseCase;

    public ProdutoController(ProdutoRepository produtoRepository, 
                             RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase,
                             ConsultarSaldoUseCase consultarSaldoUseCase) {
        this.produtoRepository = produtoRepository;
        this.registrarMovimentacaoUseCase = registrarMovimentacaoUseCase;
        this.consultarSaldoUseCase = consultarSaldoUseCase;
    }

    // 1. Cadastro de Produto (com campo 'descricao' obrigatório)
    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        Produto novoProduto = produtoRepository.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    // 2. Listagem de Todos os Produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(produtoRepository.listarTodos());
    }

    // 3. Registrar Movimentação (Entrada, Saída, Ajustes)
    @PostMapping("/{id}/movimentacoes")
    public ResponseEntity<Void> movimentar(
            @PathVariable Long id, 
            @RequestBody MovimentacaoRequest request) {
        
        registrarMovimentacaoUseCase.executar(
            id, 
            request.getQuantidade(), 
            request.getTipo(), 
            request.getOrigem()
        );
        
        return ResponseEntity.ok().build();
    }

    // 4. Nova Funcionalidade: Consultar Saldo Atual
    @GetMapping("/{id}/saldo")
    public ResponseEntity<Integer> consultarSaldo(@PathVariable Long id) {
        Integer saldo = consultarSaldoUseCase.executar(id);
        return ResponseEntity.ok(saldo);
    }
}

class MovimentacaoRequest {
    private Integer quantidade;
    private String tipo;
    private String origem;

    // Getters e Setters
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
}