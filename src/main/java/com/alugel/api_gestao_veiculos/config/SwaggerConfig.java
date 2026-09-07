package com.alugel.api_gestao_veiculos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "Bearer Token";

        return new OpenAPI()
                .info(new Info()
                        .title("API Gestão de Veículos")
                        .description("API RESTful para gestão de frota e sistema de aluguel de veículos")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Luiz Otávio")
                                .email("luiz@email.com")))
                .tags(List.of(
                        new Tag().name("Veículo").description("CRUD de veículos"),
                        new Tag().name("Cliente").description("CRUD de clientes"),
                        new Tag().name("Aluguel").description("Operações de aluguel"),
                        new Tag().name("Autenticação").description("Login e registro")
                ))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
