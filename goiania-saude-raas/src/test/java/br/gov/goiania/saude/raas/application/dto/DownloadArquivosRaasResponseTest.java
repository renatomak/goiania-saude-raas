package br.gov.goiania.saude.raas.application.dto;

import br.gov.goiania.saude.raas.mock.DownloadArquivosRaasResponseMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DownloadArquivosRaasResponseTest {

    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Test
    @DisplayName("Deve serializar dataGeracao no formato brasileiro")
    void deveSerializarDataGeracaoNoFormatoBrasileiro() throws Exception {
        DownloadArquivosRaasResponse response = DownloadArquivosRaasResponseMock.valido();

        String json = mapper.writeValueAsString(response);

        assertTrue(json.contains("\"dataGeracao\":\"07/05/2026\""));
        assertTrue(json.contains("\"nome\":"));
        assertTrue(json.contains("\"arquivo\":"));
    }
}

