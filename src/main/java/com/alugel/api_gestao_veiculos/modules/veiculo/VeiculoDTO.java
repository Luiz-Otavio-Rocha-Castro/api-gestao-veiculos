package com.alugel.api_gestao_veiculos.modules.veiculo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(name = "VeiculoDTO", description = "DTO de veículo")
public class VeiculoDTO {

    @Schema(name = "VeiculoRequest", description = "Requisição de cadastro/edição de veículo")
    public record Request(
        @Schema(description = "Marca do veículo", example = "Fiat", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Marca é obrigatória")
        String marca,

        @Schema(description = "Modelo do veículo", example = "Pulse", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Modelo é obrigatório")
        String modelo,

        @Schema(description = "Ano do veículo", example = "2024", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Ano é obrigatório")
        @Min(value = 1886, message = "Ano inválido")
        Integer ano,

        @Schema(description = "Placa do veículo (formato Mercosul)", example = "ABC1D23", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Placa é obrigatória")
        @Pattern(regexp = "[A-Z]{3}[0-9][A-Z][0-9]{2}", message = "Placa inválida (formato: ABC1D23)")
        String placa,

        @Schema(description = "Valor da diária", example = "50.00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Valor da diária é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor da diária deve ser maior que zero")
        BigDecimal valorDiaria
    ) {}

    @Schema(name = "VeiculoResponse", description = "Resposta com dados do veículo")
    public record Response(
        @Schema(description = "ID do veículo", example = "1")
        Long id,

        @Schema(description = "Marca do veículo", example = "Fiat")
        String marca,

        @Schema(description = "Modelo do veículo", example = "Pulse")
        String modelo,

        @Schema(description = "Ano do veículo", example = "2024")
        Integer ano,

        @Schema(description = "Placa do veículo", example = "ABC1D23")
        String placa,

        @Schema(description = "Valor da diária", example = "50.00")
        BigDecimal valorDiaria,

        @Schema(description = "Status do veículo", example = "DISPONIVEL")
        StatusVeiculo status
    ) {}
}
