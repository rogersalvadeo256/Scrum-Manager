package com.scrummanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String BEARER_SCHEME = "bearerAuth";
    private static final String API_KEY_SCHEME = "apiKey";

    /** Full internal spec – not linked from Scalar. */
    @Bean
    public OpenAPI openAPI() {
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
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME, jwtScheme())
                        .addSecuritySchemes(API_KEY_SCHEME, apiKeyScheme()));
    }

    /** External-only group shown in Scalar at /docs. */
    @Bean
    public GroupedOpenApi externalGroup() {
        return GroupedOpenApi.builder()
                .group("external")
                .displayName("External API")
                .pathsToMatch("/external/**")
                .addOpenApiCustomizer(api -> api
                        .info(new Info()
                                .title("Scrum Manager – External API")
                                .description("""
                                        APIs públicas para integração externa.
                                        
                                        Rotas protegidas exigem o cabeçalho `X-Api-Key` com uma chave gerada em
                                        `POST /api/users/me/api-keys`. O endpoint `/external/health` é público.
                                        """)
                                .version("1.0.0"))
                        .components(new Components()
                                .addSecuritySchemes(API_KEY_SCHEME, apiKeyScheme())))
                .build();
    }

    private static SecurityScheme jwtScheme() {
        return new SecurityScheme()
                .name(BEARER_SCHEME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Informe o JWT obtido em /api/auth/login no campo abaixo.");
    }

    private static SecurityScheme apiKeyScheme() {
        return new SecurityScheme()
                .name(API_KEY_SCHEME)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-Api-Key")
                .description("Chave de API obtida em POST /api/users/me/api-keys.");
    }
}
