package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.mock.ListarArquivosRaasMock;
import br.gov.goiania.saude.raas.mock.ListarArquivosRaasRequestMock;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListarArquivosRaasUseCaseUnitTest {

    private final ListarArquivosRaasPort repositoryPort = mock(ListarArquivosRaasPort.class);
    private final ListarArquivosRaasMapper mapper = mock(ListarArquivosRaasMapper.class);
    private final ListarArquivosRaasUseCase useCase = new ListarArquivosRaasUseCase(repositoryPort, mapper);

    @BeforeEach
    void setup() {
        org.mockito.Mockito.reset(repositoryPort, mapper);
    }

    @Test
    void listarDeveriaRetornarListaQuandoExistemDados() {
        java.util.List<ListarArquivosRaas> processos = ListarArquivosRaasMock.listaPopular();
        ListarArquivosRaasRequest request = ListarArquivosRaasRequestMock.valido();
        Page<ListarArquivosRaas> page = new PageImpl<>(processos);
        when(repositoryPort.execute(any(), any(Pageable.class))).thenReturn(page);
        when(mapper.toResponse(any())).thenReturn(null);
        assertDoesNotThrow(() -> useCase.execute(request));
        verify(repositoryPort).execute(any(), any(Pageable.class));
    }

    @Test
    void listarDeveriaRetornarListaVaziaQuandoNaoHouverDadosNoPeriodo() {
        ListarArquivosRaasRequest request = ListarArquivosRaasRequestMock.valido();
        Page<ListarArquivosRaas> emptyPage = Page.empty();
        when(repositoryPort.execute(any(), any(Pageable.class))).thenReturn(emptyPage);
        assertThrows(Exception.class, () -> useCase.execute(request));
    }

    @Test
    void listarDeveriaPropagarExcecaoQuandoRepositoryFalha() {
        ListarArquivosRaasRequest request = ListarArquivosRaasRequestMock.valido();
        when(repositoryPort.execute(any(), any(Pageable.class))).thenThrow(new RuntimeException("Erro de banco"));
        assertThrows(RuntimeException.class, () -> useCase.execute(request));
    }
}
