package br.gov.goiania.saude.raas.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SMS Goiânia - Gestão de Arquivos RAAS")
                        .version("v1.0")
                );
    }

    @Bean
    public GroupedOpenApi raasApi() {
        return GroupedOpenApi.builder()
                .group("raas")
                .pathsToMatch("/v1/raas/**")
                .build();
    }
}
