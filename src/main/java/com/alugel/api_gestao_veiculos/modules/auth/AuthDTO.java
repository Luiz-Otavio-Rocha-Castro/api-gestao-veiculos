package com.alugel.api_gestao_veiculos.modules.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDTO {

    @Schema(name = "AuthLoginRequest")
    public record Request(
        @Schema(description = "Email do usuário", example = "admin@email.com")
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Schema(description = "Senha do usuário", example = "123456")
        @NotBlank(message = "Senha é obrigatória")
        String senha
    ) {}

    @Schema(name = "AuthRegisterRequest")
    public record RegisterRequest(
        @Schema(description = "Email do usuário", example = "admin@email.com")
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Schema(description = "Senha do usuário (mínimo 6 caracteres)", example = "123456")
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha
    ) {}

    @Schema(name = "AuthResponse")
    public record Response(
        @Schema(description = "Token JWT de autenticação", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token
    ) {}
}
