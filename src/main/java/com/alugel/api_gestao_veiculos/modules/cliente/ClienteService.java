package com.alugel.api_gestao_veiculos.modules.cliente;

import com.alugel.api_gestao_veiculos.exception.exceptions.ClienteNaoEncontradoException;
import com.alugel.api_gestao_veiculos.exception.exceptions.RegistroVinculadoException;
import com.alugel.api_gestao_veiculos.modules.aluguel.AluguelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final AluguelRepository aluguelRepository;

    public ClienteDTO.Response cadastrar(ClienteDTO.Request request) {
        Cliente cliente = new Cliente();
        cliente.setCpf(request.cpf());
        cliente.setNome(request.nome());
        cliente.setTelefone(request.telefone());

        cliente = clienteRepository.save(cliente);
        return toResponse(cliente);
    }

    public List<ClienteDTO.Response> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ClienteDTO.Response buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com id: " + id));
        return toResponse(cliente);
    }

    public ClienteDTO.Response buscarPorCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com cpf: " + cpf));
        return toResponse(cliente);
    }

    public ClienteDTO.Response editar(Long id, ClienteDTO.Request request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com id: " + id));

        cliente.setCpf(request.cpf());
        cliente.setNome(request.nome());
        cliente.setTelefone(request.telefone());

        cliente = clienteRepository.save(cliente);
        return toResponse(cliente);
    }

    public void remover(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com id: " + id));

        if (!aluguelRepository.findByClienteId(id).isEmpty()) {
            throw new RegistroVinculadoException("Não é possível remover cliente vinculado a aluguéis");
        }

        clienteRepository.delete(cliente);
    }

    private ClienteDTO.Response toResponse(Cliente cliente) {
        return new ClienteDTO.Response(
                cliente.getId(),
                cliente.getCpf(),
                cliente.getNome(),
                cliente.getTelefone()
        );
    }
}
