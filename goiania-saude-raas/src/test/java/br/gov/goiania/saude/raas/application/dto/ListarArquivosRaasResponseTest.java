package br.gov.goiania.saude.raas.application.dto;

import br.gov.goiania.saude.raas.mock.ListarArquivosRaasResponseMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ListarArquivosRaasResponseTest {

    private final ObjectMapper mapper = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .findAndAddModules()
            .build();

    @Test
    @DisplayName("Deve serializar data_geracao no formato brasileiro")
    void deveSerializarDataGeracaoNoFormatoBrasileiro() throws Exception {
        ListarArquivosRaasResponse response = ListarArquivosRaasResponseMock.valido();

        String json = mapper.writeValueAsString(response);

        assertTrue(json.contains("\"data_geracao\":\"07/05/2026\""));
    }
}

