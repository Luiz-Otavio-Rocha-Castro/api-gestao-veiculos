package com.alugel.api_gestao_veiculos.modules.cliente;

import jakarta.validation.constraints.*;

public class ClienteDTO {

    public record Request(
        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone
    ) {}

    public record Response(
        Long id,
        String cpf,
        String nome,
        String telefone
    ) {}
}
