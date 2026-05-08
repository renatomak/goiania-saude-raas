package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.mock.ListarArquivosRaasMock;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListarArquivosRaasMapperTest {

    private final ListarArquivosRaasMapper mapper = Mappers.getMapper(ListarArquivosRaasMapper.class);

    @Test
    void toResponseDeveManterIntegridadeDosDados() {
        ListarArquivosRaas domain = ListarArquivosRaasMock.valido();
        var response = mapper.toResponse(domain);
        assertEquals(domain.getId(), response.id());
        assertEquals(domain.getCodigoEmpresa(), response.codigoEmpresa());
        assertEquals(domain.getNomeEmpresa(), response.nomeEmpresa());
        assertEquals(domain.getDataGeracao(), response.dataGeracao());
        assertEquals(domain.getPath(), response.path());
        assertEquals(domain.getStatus(), response.status());
        assertEquals(domain.getTotalFolha(), response.totalFolha());
    }
}
