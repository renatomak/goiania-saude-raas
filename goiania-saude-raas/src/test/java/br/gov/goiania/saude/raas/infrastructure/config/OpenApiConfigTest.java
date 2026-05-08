package br.gov.goiania.saude.raas.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springdoc.core.models.GroupedOpenApi;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class OpenApiConfigTest {

    @Test
    @DisplayName("Deve criar bean customOpenAPI com título e versão corretos")
    void deveCriarBeanCustomOpenAPI() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI openAPI = config.customOpenAPI();
        assertNotNull(openAPI);
        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("SMS Goiânia - Gestão de Arquivos RAAS", info.getTitle());
        assertEquals("v1.0", info.getVersion());
    }

    @Test
    @DisplayName("Deve criar bean GroupedOpenApi com grupo e path corretos")
    void deveCriarBeanGroupedOpenApi() {
        OpenApiConfig config = new OpenApiConfig();
        GroupedOpenApi groupedOpenApi = config.raasApi();
        assertNotNull(groupedOpenApi);
        assertEquals("raas", groupedOpenApi.getGroup());
    }
}

