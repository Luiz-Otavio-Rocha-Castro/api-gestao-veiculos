package com.alugel.api_gestao_veiculos.modules.aluguel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alugueis")
@RequiredArgsConstructor
public class AluguelController {

    private final AluguelService aluguelService;

    @PostMapping
    public ResponseEntity<AluguelDTO.Response> cadastrar(@RequestBody @Valid AluguelDTO.Request request) {
        AluguelDTO.Response response = aluguelService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AluguelDTO.Response>> listarTodos() {
        List<AluguelDTO.Response> response = aluguelService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AluguelDTO.Response> buscarPorId(@PathVariable Long id) {
        AluguelDTO.Response response = aluguelService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/veiculo/{veiculoId}")
    public ResponseEntity<List<AluguelDTO.Response>> buscarPorVeiculo(@PathVariable Long veiculoId) {
        List<AluguelDTO.Response> response = aluguelService.buscarPorVeiculo(veiculoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AluguelDTO.Response>> buscarPorCliente(@PathVariable Long clienteId) {
        List<AluguelDTO.Response> response = aluguelService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(response);
    }
}
