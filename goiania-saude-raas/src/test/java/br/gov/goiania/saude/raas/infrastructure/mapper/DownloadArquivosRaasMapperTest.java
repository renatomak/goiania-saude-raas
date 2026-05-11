package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.domain.model.DownloadArquivosRaas;
import br.gov.goiania.saude.raas.mock.DownloadArquivosRaasMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DownloadArquivosRaasMapperTest {

    private final DownloadArquivosRaasMapper mapper =
            Mappers.getMapper(DownloadArquivosRaasMapper.class);

    @Test
    @DisplayName("Deve manter integridade dos dados ao converter para response")
    void toResponseDeveManterIntegridadeDosDados() {
        DownloadArquivosRaas domain = DownloadArquivosRaasMock.valido();
        var response = mapper.toResponse(domain);

        assertEquals(domain.getId(), response.id());
        assertEquals(domain.getPath(), response.path());
        assertEquals(domain.getDataGeracao(), response.dataGeracao());
        assertEquals(domain.getTexto(), response.texto());
    }

    @Test
    @DisplayName("Deve preservar todos os campos do domínio")
    void devePreservarTodosCamposDoDominio() {
        DownloadArquivosRaas domain = DownloadArquivosRaasMock.valido();
        var response = mapper.toResponse(domain);

        assertEquals(1L, response.id());
        assertNotNull(response.path());
        assertNotNull(response.dataGeracao());
        assertNotNull(response.texto());
    }

    private void assertNotNull(Object obj) {
        if (obj == null) {
            throw new AssertionError("Expected non-null value");
        }
    }
}

