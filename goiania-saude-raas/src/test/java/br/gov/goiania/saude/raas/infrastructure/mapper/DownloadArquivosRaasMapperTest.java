package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.domain.model.DownloadArquivosRaas;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload.DownloadArquivosRaasProjection;
import br.gov.goiania.saude.raas.mock.DownloadArquivosRaasMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    @DisplayName("Deve retornar null quando domain é null no toResponse")
    void deveRetornarNullQuandoDomainNullEmToResponse() {
        var response = mapper.toResponse(null);
        assertNull(response);
    }

    @Test
    @DisplayName("Deve retornar null quando projection é null no toDomain")
    void deveRetornarNullQuandoProjectionNullEmToDomain() {
        var domain = mapper.toDomain((DownloadArquivosRaasProjection) null);
        assertNull(domain);
    }

    @Test
    @DisplayName("Deve mapear campos null da projection para domínio")
    void deveMapearCamposNullDaProjection() {
        DownloadArquivosRaasProjection projection =
                new DownloadArquivosRaasProjection() {
                    @Override
                    public Long getId() {
                        return null;
                    }

                    @Override
                    public LocalDate getDtGeracao() {
                        return null;
                    }

                    @Override
                    public String getPath() {
                        return null;
                    }

                    @Override
                    public String getTexto() {
                        return null;
                    }
                };

        var domain = mapper.toDomain(projection);

        assertNull(domain.getId());
        assertNull(domain.getDataGeracao());
        assertNull(domain.getPath());
        assertNull(domain.getTexto());
    }

    @Test
    @DisplayName("Deve mapear campos null do domínio para response")
    void deveMapearCamposNullDoDominio() {
        DownloadArquivosRaas domain =
                new DownloadArquivosRaas(null, null, null, null);

        var response = mapper.toResponse(domain);

        assertNull(response.id());
        assertNull(response.dataGeracao());
        assertNull(response.path());
        assertNull(response.texto());
    }

    private void assertNotNull(Object obj) {
        if (obj == null) {
            throw new AssertionError("Expected non-null value");
        }
    }
}

