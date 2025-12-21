package com.movemais.estoque.api.controller;

import com.movemais.estoque.api.dto.MovimentacaoRequest;
import com.movemais.estoque.application.usecases.RegistrarMovimentacaoUseCase;
import com.movemais.estoque.application.usecases.ListarMovimentacoesUseCase;
import com.movemais.estoque.infrastructure.persistence.MovimentacaoEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos") // A base agora é produtos
public class MovimentacaoController {

    private final RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase;
    private final ListarMovimentacoesUseCase listarMovimentacoesUseCase;

    public MovimentacaoController(
            RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase, 
            ListarMovimentacoesUseCase listarMovimentacoesUseCase) {
        this.registrarMovimentacaoUseCase = registrarMovimentacaoUseCase;
        this.listarMovimentacoesUseCase = listarMovimentacoesUseCase;
    }

    // POST: /api/produtos/{id}/movimentacoes
    @PostMapping("/{id}/movimentacoes")
    public ResponseEntity<Object> registrar(
            @PathVariable Long id, 
            @RequestBody @Valid MovimentacaoRequest request) {
        try {
            // Usamos o id que vem do path da URL
            registrarMovimentacaoUseCase.executar(id, request.quantidade(), request.tipo());
            return ResponseEntity.ok("Movimentação realizada com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // GET: /api/produtos/{id}/movimentacoes
    @GetMapping("/{id}/movimentacoes")
    public ResponseEntity<List<MovimentacaoEntity>> listar(@PathVariable Long id) {
        List<MovimentacaoEntity> historico = listarMovimentacoesUseCase.executar(id);
        return ResponseEntity.ok(historico);
    }
}