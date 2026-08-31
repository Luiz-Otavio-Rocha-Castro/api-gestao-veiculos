package com.alugel.api_gestao_veiculos.modules.cliente;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO.Response> cadastrar(@RequestBody @Valid ClienteDTO.Request request) {
        ClienteDTO.Response response = clienteService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO.Response>> listarTodos() {
        List<ClienteDTO.Response> response = clienteService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO.Response> buscarPorId(@PathVariable Long id) {
        ClienteDTO.Response response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteDTO.Response> buscarPorCpf(@PathVariable String cpf) {
        ClienteDTO.Response response = clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO.Response> editar(@PathVariable Long id, @RequestBody @Valid ClienteDTO.Request request) {
        ClienteDTO.Response response = clienteService.editar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        clienteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
