package com.example.inventory.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;
@Configuration
public class OpenApiServerConfig {
    @Bean
    public OpenApiCustomizer globalServerCustomizer() {
        return openApi -> {
        // 1. Enforce Gateway Proxy Routing (From our previous step)
            if (openApi.getServers() != null) {
                openApi.getServers().clear();
            }
            Server relativeServer = new Server();
            relativeServer.setUrl("/");
            relativeServer.setDescription("API Gateway Proxy Route");
            openApi.setServers(new ArrayList<>(List.of(relativeServer)));
        // 2. Add the Bearer Token Security Scheme (Creates the "Authorize" Button)
            if (openApi.getComponents() == null) {
                openApi.setComponents(new Components());
            }
            openApi.getComponents().addSecuritySchemes("BearerAuthentication",
                    new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .description("Enter your valid JWT bearer token here."));
        // 3. Apply the lock icon globally to all endpoints in this service
            openApi.addSecurityItem(new SecurityRequirement().addList("BearerAuthentication"));
        };
    }
}