package com.alugel.api_gestao_veiculos.modules.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "ClienteDTO", description = "DTO de cliente")
public class ClienteDTO {

    @Schema(name = "ClienteRequest", description = "Requisição de cadastro/edição de cliente")
    public record Request(
        @Schema(description = "CPF do cliente", example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @Schema(description = "Nome do cliente", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Schema(description = "Telefone do cliente", example = "11999998888", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Telefone é obrigatório")
        String telefone
    ) {}

    @Schema(name = "ClienteResponse", description = "Resposta com dados do cliente")
    public record Response(
        @Schema(description = "ID do cliente", example = "1")
        Long id,

        @Schema(description = "CPF do cliente", example = "12345678901")
        String cpf,

        @Schema(description = "Nome do cliente", example = "João da Silva")
        String nome,

        @Schema(description = "Telefone do cliente", example = "11999998888")
        String telefone
    ) {}
}
