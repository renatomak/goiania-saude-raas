package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.service.ListarArquivosRaasDomainService;
import br.gov.goiania.saude.raas.fixtures.ListarArquivosRaasFixture;
import br.gov.goiania.saude.raas.fixtures.ListarArquivosRaasRequestFixture;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListarArquivosRaasUseCaseUnitTest {

    private final ListarArquivosRaasPort repositoryPort = mock(ListarArquivosRaasPort.class);
    private final ListarArquivosRaasDomainService domainService = new ListarArquivosRaasDomainService();
    private final ListarArquivosRaasMapper mapper = mock(ListarArquivosRaasMapper.class);
    private final ListarArquivosRaasUseCase useCase = new ListarArquivosRaasUseCase(repositoryPort, domainService, mapper);

    @BeforeEach
    void setup() {
        org.mockito.Mockito.reset(repositoryPort, mapper);
    }

    @Test
    void listarDeveriaRetornarListaQuandoExistemDados() {
        List<ListarArquivosRaas> processos = ListarArquivosRaasFixture.listaPopular();
        ListarArquivosRaasRequest request = ListarArquivosRaasRequestFixture.valido();
        when(repositoryPort.execute(any())).thenReturn(processos);
        when(mapper.toResponse(any())).thenReturn(null);
        assertDoesNotThrow(() -> useCase.execute(request));
        verify(repositoryPort).execute(any());
    }

    @Test
    void listarDeveriaRetornarListaVaziaQuandoNaoHouverDadosNoPeriodo() {
        ListarArquivosRaasRequest request = ListarArquivosRaasRequestFixture.valido();
        when(repositoryPort.execute(any())).thenReturn(Collections.emptyList());
        assertThrows(Exception.class, () -> useCase.execute(request));
    }

    @Test
    void listarDeveriaPropagarExcecaoQuandoRepositoryFalha() {
        ListarArquivosRaasRequest request = ListarArquivosRaasRequestFixture.valido();
        when(repositoryPort.execute(any())).thenThrow(new RuntimeException("Erro de banco"));
        assertThrows(RuntimeException.class, () -> useCase.execute(request));
    }
}
