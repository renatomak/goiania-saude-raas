package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.fixtures.ListarArquivosRaasFixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.gov.goiania.saude.raas.testutils.TestConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListarArquivosRaasMapperTest {

    private final ListarArquivosRaasMapper mapper = Mappers.getMapper(ListarArquivosRaasMapper.class);

    @BeforeAll
    static void setup() {
        FixtureFactoryLoader.loadTemplates(TestConstants.FIXTURE_PATH);
    }

    @Test
    void toResponseDeveManterIntegridadeDosDados() {
        ListarArquivosRaas domain = ListarArquivosRaasFixture.valido();
        var response = mapper.toResponse(domain);
        assertEquals(domain.getCodigoEmpresa(), response.codigoEmpresa());
        assertEquals(domain.getNomeEmpresa(), response.nomeEmpresa());
        assertEquals(domain.getDataGeracao(), response.dataGeracao());
        assertEquals(domain.getPath(), response.path());
        assertEquals(domain.getStatus(), response.status());
        assertEquals(domain.getTotalFolha(), response.totalFolha());
    }
}
