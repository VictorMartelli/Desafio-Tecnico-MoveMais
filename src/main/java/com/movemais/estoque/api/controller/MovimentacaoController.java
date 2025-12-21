package com.movemais.estoque.api.controller;

import com.movemais.estoque.api.dto.MovimentacaoRequest;
import com.movemais.estoque.application.usecases.RegistrarMovimentacaoUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    private final RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase;

    public MovimentacaoController(RegistrarMovimentacaoUseCase registrarMovimentacaoUseCase) {
        this.registrarMovimentacaoUseCase = registrarMovimentacaoUseCase;
    }

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody @Valid MovimentacaoRequest request) {
        try {
            registrarMovimentacaoUseCase.executar(
                request.produtoId(), 
                request.quantidade(), 
                request.tipo()
            );
            return ResponseEntity.ok("Movimentação realizada com sucesso!");
        } catch (IllegalStateException e) {
            // Retorna o erro de negócio (ex: Saldo Insuficiente) com Status 400
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }
}