package com.alugel.api_gestao_veiculos.modules.aluguel;

import com.alugel.api_gestao_veiculos.exception.exceptions.*;
import com.alugel.api_gestao_veiculos.modules.cliente.Cliente;
import com.alugel.api_gestao_veiculos.modules.cliente.ClienteRepository;
import com.alugel.api_gestao_veiculos.modules.veiculo.StatusVeiculo;
import com.alugel.api_gestao_veiculos.modules.veiculo.Veiculo;
import com.alugel.api_gestao_veiculos.modules.veiculo.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    public AluguelDTO.Response cadastrar(AluguelDTO.Request request) {
        Veiculo veiculo = veiculoRepository.findById(request.veiculoId())
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado com id: " + request.veiculoId()));

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com id: " + request.clienteId()));

        if (veiculo.getStatus() != StatusVeiculo.DISPONIVEL) {
            throw new VeiculoIndisponivelException("Veículo não está disponível para aluguel");
        }

        if (request.dataFim().isBefore(request.dataInicio())) {
            throw new DataInvalidaException("Data de fim não pode ser anterior à data de início");
        }

        long dias = ChronoUnit.DAYS.between(request.dataInicio(), request.dataFim());
        if (dias <= 0) {
            throw new DataInvalidaException("O aluguel deve ter pelo menos 1 dia");
        }

        BigDecimal valorTotal = veiculo.getValorDiaria().multiply(BigDecimal.valueOf(dias));

        Aluguel aluguel = new Aluguel();
        aluguel.setDataInicio(request.dataInicio());
        aluguel.setDataFim(request.dataFim());
        aluguel.setValorTotal(valorTotal);
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);

        veiculo.setStatus(StatusVeiculo.ALUGADO);
        veiculoRepository.save(veiculo);

        aluguel = aluguelRepository.save(aluguel);
        return toResponse(aluguel);
    }

    public List<AluguelDTO.Response> listarTodos() {
        return aluguelRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AluguelDTO.Response buscarPorId(Long id) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new AluguelNaoEncontradoException("Aluguel não encontrado com id: " + id));
        return toResponse(aluguel);
    }

    public List<AluguelDTO.Response> buscarPorVeiculo(Long veiculoId) {
        return aluguelRepository.findByVeiculoId(veiculoId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AluguelDTO.Response> buscarPorCliente(Long clienteId) {
        return aluguelRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private AluguelDTO.Response toResponse(Aluguel aluguel) {
        AluguelDTO.VeiculoResumo veiculoResumo = new AluguelDTO.VeiculoResumo(
                aluguel.getVeiculo().getId(),
                aluguel.getVeiculo().getMarca(),
                aluguel.getVeiculo().getModelo(),
                aluguel.getVeiculo().getPlaca(),
                aluguel.getVeiculo().getValorDiaria()
        );

        AluguelDTO.ClienteResumo clienteResumo = new AluguelDTO.ClienteResumo(
                aluguel.getCliente().getId(),
                aluguel.getCliente().getNome(),
                aluguel.getCliente().getCpf()
        );

        return new AluguelDTO.Response(
                aluguel.getId(),
                aluguel.getDataInicio(),
                aluguel.getDataFim(),
                aluguel.getValorTotal(),
                veiculoResumo,
                clienteResumo
        );
    }
}
