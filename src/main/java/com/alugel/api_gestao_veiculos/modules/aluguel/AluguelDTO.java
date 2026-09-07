package com.alugel.api_gestao_veiculos.modules.aluguel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(name = "AluguelDTO", description = "DTO de aluguel")
public class AluguelDTO {

    @Schema(name = "AluguelRequest", description = "Requisição de cadastro de aluguel")
    public record Request(
        @Schema(description = "Data de início do aluguel", example = "2026-09-10", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        @Schema(description = "Data de fim do aluguel", example = "2026-09-15", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Data de fim é obrigatória")
        LocalDate dataFim,

        @Schema(description = "ID do veículo", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "ID do veículo é obrigatório")
        Long veiculoId,

        @Schema(description = "ID do cliente", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "ID do cliente é obrigatório")
        Long clienteId
    ) {}

    @Schema(name = "AluguelResponse", description = "Resposta com dados do aluguel")
    public record Response(
        @Schema(description = "ID do aluguel", example = "1")
        Long id,

        @Schema(description = "Data de início do aluguel", example = "2026-09-10")
        LocalDate dataInicio,

        @Schema(description = "Data de fim do aluguel", example = "2026-09-15")
        LocalDate dataFim,

        @Schema(description = "Valor total do aluguel", example = "250.00")
        BigDecimal valorTotal,

        @Schema(description = "Dados resumidos do veículo")
        VeiculoResumo veiculo,

        @Schema(description = "Dados resumidos do cliente")
        ClienteResumo cliente
    ) {}

    @Schema(name = "VeiculoResumoAluguel", description = "Resumo do veículo no aluguel")
    public record VeiculoResumo(
        @Schema(description = "ID do veículo", example = "1")
        Long id,

        @Schema(description = "Marca do veículo", example = "Fiat")
        String marca,

        @Schema(description = "Modelo do veículo", example = "Pulse")
        String modelo,

        @Schema(description = "Placa do veículo", example = "ABC1D23")
        String placa,

        @Schema(description = "Valor da diária", example = "50.00")
        BigDecimal valorDiaria
    ) {}

    @Schema(name = "ClienteResumoAluguel", description = "Resumo do cliente no aluguel")
    public record ClienteResumo(
        @Schema(description = "ID do cliente", example = "1")
        Long id,

        @Schema(description = "Nome do cliente", example = "João da Silva")
        String nome,

        @Schema(description = "CPF do cliente", example = "12345678901")
        String cpf
    ) {}
}
