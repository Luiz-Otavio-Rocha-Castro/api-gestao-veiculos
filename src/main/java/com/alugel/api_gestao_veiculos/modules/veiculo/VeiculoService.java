package com.alugel.api_gestao_veiculos.modules.veiculo;

import com.alugel.api_gestao_veiculos.exception.exceptions.RegistroVinculadoException;
import com.alugel.api_gestao_veiculos.exception.exceptions.VeiculoNaoEncontradoException;
import com.alugel.api_gestao_veiculos.modules.aluguel.AluguelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final AluguelRepository aluguelRepository;

    public VeiculoDTO.Response cadastrar(VeiculoDTO.Request request) {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(request.marca());
        veiculo.setModelo(request.modelo());
        veiculo.setAno(request.ano());
        veiculo.setPlaca(request.placa());
        veiculo.setValorDiaria(request.valorDiaria());
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);

        veiculo = veiculoRepository.save(veiculo);
        return toResponse(veiculo);
    }

    public List<VeiculoDTO.Response> listarTodos() {
        return veiculoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public VeiculoDTO.Response buscarPorId(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado com id: " + id));
        return toResponse(veiculo);
    }

    public VeiculoDTO.Response buscarPorPlaca(String placa) {
        Veiculo veiculo = veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado com placa: " + placa));
        return toResponse(veiculo);
    }

    public VeiculoDTO.Response editar(Long id, VeiculoDTO.Request request) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado com id: " + id));

        veiculo.setMarca(request.marca());
        veiculo.setModelo(request.modelo());
        veiculo.setAno(request.ano());
        veiculo.setPlaca(request.placa());
        veiculo.setValorDiaria(request.valorDiaria());

        veiculo = veiculoRepository.save(veiculo);
        return toResponse(veiculo);
    }

    public void remover(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado com id: " + id));

        if (!aluguelRepository.findByVeiculoId(id).isEmpty()) {
            throw new RegistroVinculadoException("Não é possível remover veículo vinculado a aluguéis");
        }

        veiculoRepository.delete(veiculo);
    }

    private VeiculoDTO.Response toResponse(Veiculo veiculo) {
        return new VeiculoDTO.Response(
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAno(),
                veiculo.getPlaca(),
                veiculo.getValorDiaria(),
                veiculo.getStatus()
        );
    }
}
