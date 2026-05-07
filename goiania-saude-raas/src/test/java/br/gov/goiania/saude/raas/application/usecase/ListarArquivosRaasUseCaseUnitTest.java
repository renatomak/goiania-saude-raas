package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.service.ListarArquivosRaasDomainService;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import br.gov.goiania.saude.raas.fixtures.ListarArquivosRaasFixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static br.gov.goiania.saude.raas.testutils.TestConstants.FIXTURE_PATH;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListarArquivosRaasUseCaseUnitTest {

    private final ListarArquivosRaasPort repositoryPort = mock(ListarArquivosRaasPort.class);
    private final ListarArquivosRaasDomainService domainService = new ListarArquivosRaasDomainService();
    private final ListarArquivosRaasMapper mapper = mock(ListarArquivosRaasMapper.class);
    private final ListarArquivosRaasUseCase useCase = new ListarArquivosRaasUseCase(repositoryPort, domainService, mapper);

    @BeforeAll
    static void setup() {
        FixtureFactoryLoader.loadTemplates(FIXTURE_PATH);
    }

    @Test
    void listarDeveriaRetornarListaQuandoExistemDados() {
        List<ListarArquivosRaas> processos = ListarArquivosRaasFixture.listaPopular();
        when(repositoryPort.execute(any())).thenReturn(processos);
        when(mapper.toResponse(any())).thenReturn(null);
        ListarArquivosRaasRequest request = new ListarArquivosRaasRequest(5, 2026, "123", null);
        assertDoesNotThrow(() -> useCase.execute(request));
        verify(repositoryPort).execute(any());
    }

    @Test
    void listarDeveriaRetornarListaVaziaQuandoNaoHouverDadosNoPeriodo() {
        when(repositoryPort.execute(any())).thenReturn(Collections.emptyList());
        when(mapper.toResponse(any())).thenReturn(null);
        ListarArquivosRaasRequest request = new ListarArquivosRaasRequest(5, 2026, "123", null);
        List<?> resultado = useCase.execute(request);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void listarDeveriaPropagarExcecaoQuandoRepositoryFalha() {
        when(repositoryPort.execute(any())).thenThrow(new RuntimeException("Erro de banco"));
        ListarArquivosRaasRequest request = new ListarArquivosRaasRequest(5, 2026, "123", null);
        assertThrows(RuntimeException.class, () -> useCase.execute(request));
    }
}
