package com.scrummanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Scrum Manager API")
                        .description("""
                                API REST para gestão de projetos Scrum.
                                
                                Permite criar projetos, gerenciar sprints, tarefas e membros,
                                com autenticação JWT, cache Redis e eventos RabbitMQ.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Scrum Manager")
                                .url("https://github.com/rogersalvadeo256/Scrum-Manager")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Informe o JWT obtido em /api/auth/login no campo abaixo.")));
    }
}
