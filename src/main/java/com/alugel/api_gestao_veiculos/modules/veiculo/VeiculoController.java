package com.alugel.api_gestao_veiculos.modules.veiculo;

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
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
@Tag(name = "Veículo", description = "CRUD de veículos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    @Operation(summary = "Cadastrar veículo", description = "Cadastra um novo veículo no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veículo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<VeiculoDTO.Response> cadastrar(@RequestBody @Valid VeiculoDTO.Request request) {
        VeiculoDTO.Response response = veiculoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar veículos", description = "Retorna a lista de todos os veículos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<VeiculoDTO.Response>> listarTodos() {
        List<VeiculoDTO.Response> response = veiculoService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veículo por ID", description = "Retorna um veículo pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public ResponseEntity<VeiculoDTO.Response> buscarPorId(
            @Parameter(description = "ID do veículo") @PathVariable Long id) {
        VeiculoDTO.Response response = veiculoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Buscar veículo por placa", description = "Retorna um veículo pela placa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public ResponseEntity<VeiculoDTO.Response> buscarPorPlaca(
            @Parameter(description = "Placa do veículo") @PathVariable String placa) {
        VeiculoDTO.Response response = veiculoService.buscarPorPlaca(placa);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar veículo", description = "Atualiza as informações de um veículo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public ResponseEntity<VeiculoDTO.Response> editar(
            @Parameter(description = "ID do veículo") @PathVariable Long id,
            @RequestBody @Valid VeiculoDTO.Request request) {
        VeiculoDTO.Response response = veiculoService.editar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover veículo", description = "Remove um veículo do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Veículo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
            @ApiResponse(responseCode = "409", description = "Veículo vinculado a aluguéis")
    })
    public ResponseEntity<Void> remover(
            @Parameter(description = "ID do veículo") @PathVariable Long id) {
        veiculoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
