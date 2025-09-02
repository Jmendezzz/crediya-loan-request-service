package co.com.crediya.api.config;

import co.com.crediya.api.constant.ApiResource;
import co.com.crediya.api.constant.ApiVersion;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI crediyaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CrediYa Loan Application Service API")
                        .description("API para solicitud de prestamos de CrediYa")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@crediya.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación completa del proyecto")
                        .url("https://tu-repo.gitlab.com/crediya/docs"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("Solicitudes de Prestamo")
                .pathsToMatch(ApiVersion.V1.getVersion() + ApiResource.LOAN_APPLICATIONS.getResource() + "/**")
                .build();
    }
}