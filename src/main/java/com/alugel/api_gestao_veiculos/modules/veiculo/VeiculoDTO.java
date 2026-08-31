package com.alugel.api_gestao_veiculos.modules.veiculo;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class VeiculoDTO {

    public record Request(
        @NotBlank(message = "Marca é obrigatória")
        String marca,

        @NotBlank(message = "Modelo é obrigatório")
        String modelo,

        @NotNull(message = "Ano é obrigatório")
        @Min(value = 1886, message = "Ano inválido")
        Integer ano,

        @NotBlank(message = "Placa é obrigatória")
        @Pattern(regexp = "[A-Z]{3}[0-9][A-Z0-9]{2}", message = "Placa inválida (formato: ABC1D23)")
        String placa,

        @NotNull(message = "Valor da diária é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor da diária deve ser maior que zero")
        BigDecimal valorDiaria
    ) {}

    public record Response(
        Long id,
        String marca,
        String modelo,
        Integer ano,
        String placa,
        BigDecimal valorDiaria,
        StatusVeiculo status
    ) {}
}
