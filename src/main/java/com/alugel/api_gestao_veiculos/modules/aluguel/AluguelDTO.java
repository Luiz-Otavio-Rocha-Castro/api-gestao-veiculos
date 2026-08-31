package com.alugel.api_gestao_veiculos.modules.aluguel;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AluguelDTO {

    public record Request(
        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        @NotNull(message = "Data de fim é obrigatória")
        LocalDate dataFim,

        @NotNull(message = "ID do veículo é obrigatório")
        Long veiculoId,

        @NotNull(message = "ID do cliente é obrigatório")
        Long clienteId
    ) {}

    public record Response(
        Long id,
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal valorTotal,
        VeiculoResumo veiculo,
        ClienteResumo cliente
    ) {}

    public record VeiculoResumo(
        Long id,
        String marca,
        String modelo,
        String placa,
        BigDecimal valorDiaria
    ) {}

    public record ClienteResumo(
        Long id,
        String nome,
        String cpf
    ) {}
}
