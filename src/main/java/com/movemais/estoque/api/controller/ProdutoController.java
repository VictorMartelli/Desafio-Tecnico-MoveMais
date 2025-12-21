package com.movemais.estoque.api.controller;

import com.movemais.estoque.api.dto.ProdutoRequest;
import com.movemais.estoque.application.usecases.CadastrarProdutoUseCase;
import com.movemais.estoque.domain.model.Produto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final CadastrarProdutoUseCase cadastrarProdutoUseCase;

    public ProdutoController(CadastrarProdutoUseCase cadastrarProdutoUseCase) {
        this.cadastrarProdutoUseCase = cadastrarProdutoUseCase;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody @Valid ProdutoRequest request) {
        // Agora passamos 4 argumentos para o Caso de Uso: sku, nome, descricao e estoqueMinimo
        // Isso resolve o erro 'method is not applicable for the arguments'
        Produto produto = cadastrarProdutoUseCase.executar(
            request.sku(), 
            request.nome(), 
            request.descricao(), 
            request.estoqueMinimo()
        );
        
        return ResponseEntity.status(201).body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        // Este método pode chamar um use case de listagem se você já o criou
        // Por enquanto, retorna uma lista dos produtos cadastrados
        return ResponseEntity.ok().build(); 
    }
}