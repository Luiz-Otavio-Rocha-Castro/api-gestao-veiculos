package com.alugel.api_gestao_veiculos.modules.aluguel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alugueis")
@RequiredArgsConstructor
@Tag(name = "Aluguel", description = "Operações de aluguel de veículos")
public class AluguelController {

    private final AluguelService aluguelService;

    @PostMapping
    @Operation(summary = "Alugar veículo", description = "Registra um novo aluguel de veículo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Aluguel registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Veículo ou cliente não encontrado"),
            @ApiResponse(responseCode = "409", description = "Veículo não está disponível")
    })
    public ResponseEntity<AluguelDTO.Response> cadastrar(@RequestBody @Valid AluguelDTO.Request request) {
        AluguelDTO.Response response = aluguelService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar aluguéis", description = "Retorna a lista de todos os aluguéis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<AluguelDTO.Response>> listarTodos() {
        List<AluguelDTO.Response> response = aluguelService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluguel por ID", description = "Retorna um aluguel pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aluguel encontrado"),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado")
    })
    public ResponseEntity<AluguelDTO.Response> buscarPorId(
            @Parameter(description = "ID do aluguel") @PathVariable Long id) {
        AluguelDTO.Response response = aluguelService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/veiculo/{veiculoId}")
    @Operation(summary = "Buscar aluguéis por veículo", description = "Retorna todos os aluguéis de um veículo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public ResponseEntity<List<AluguelDTO.Response>> buscarPorVeiculo(
            @Parameter(description = "ID do veículo") @PathVariable Long veiculoId) {
        List<AluguelDTO.Response> response = aluguelService.buscarPorVeiculo(veiculoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar aluguéis por cliente", description = "Retorna todos os aluguéis de um cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<List<AluguelDTO.Response>> buscarPorCliente(
            @Parameter(description = "ID do cliente") @PathVariable Long clienteId) {
        List<AluguelDTO.Response> response = aluguelService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(response);
    }
}
