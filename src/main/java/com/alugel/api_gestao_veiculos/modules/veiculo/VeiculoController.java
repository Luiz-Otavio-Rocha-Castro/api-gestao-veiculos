package com.alugel.api_gestao_veiculos.modules.veiculo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<VeiculoDTO.Response> cadastrar(@RequestBody @Valid VeiculoDTO.Request request) {
        VeiculoDTO.Response response = veiculoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoDTO.Response>> listarTodos() {
        List<VeiculoDTO.Response> response = veiculoService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO.Response> buscarPorId(@PathVariable Long id) {
        VeiculoDTO.Response response = veiculoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<VeiculoDTO.Response> buscarPorPlaca(@PathVariable String placa) {
        VeiculoDTO.Response response = veiculoService.buscarPorPlaca(placa);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO.Response> editar(@PathVariable Long id, @RequestBody @Valid VeiculoDTO.Request request) {
        VeiculoDTO.Response response = veiculoService.editar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        veiculoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
