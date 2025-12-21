package com.movemais.estoque.api.controller;

import com.movemais.estoque.api.dto.MovimentacaoRequest;
import com.movemais.estoque.application.usecases.RegistrarMovimentacaoUseCase;
import com.movemais.estoque.application.usecases.ListarMovimentacoesUseCase;
import com.movemais.estoque.infrastructure.persistence.MovimentacaoEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController
@RequestMapping("/api/produtos")
public class MovimentacaoController {

    private final RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase;
    private final ListarMovimentacoesUseCase listarMovimentacoesUseCase;

    public MovimentacaoController(
            RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase, 
            ListarMovimentacoesUseCase listarMovimentacoesUseCase) {
        this.registrarMovimentacaoUseCase = registrarMovimentacaoUseCase;
        this.listarMovimentacoesUseCase = listarMovimentacoesUseCase;
    }

    /**
     * POST /api/produtos/{id}/movimentacoes
     * Registra uma entrada, saída ou ajuste de estoque.
     */
    @PostMapping("/{id}/movimentacoes")
    public ResponseEntity<Object> registrar(
            @PathVariable Long id, 
            @RequestBody @Valid MovimentacaoRequest request) {
        try {
            // Agora passamos o campo 'origem' vindo do DTO
            registrarMovimentacaoUseCase.executar(
                id, 
                request.quantidade(), 
                request.tipo(), 
                request.origem() 
            );
            return ResponseEntity.ok("Movimentação realizada com sucesso!");
        } catch (IllegalStateException e) {
            // Captura erros de saldo insuficiente
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            // Captura erros de produto não encontrado
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * GET /api/produtos/{id}/movimentacoes
     * Retorna o histórico completo de movimentações do produto.
     */
    @GetMapping("/{id}/movimentacoes")
    public ResponseEntity<List<MovimentacaoEntity>> listar(@PathVariable Long id) {
        List<MovimentacaoEntity> historico = listarMovimentacoesUseCase.executar(id);
        return ResponseEntity.ok(historico);
    }
}