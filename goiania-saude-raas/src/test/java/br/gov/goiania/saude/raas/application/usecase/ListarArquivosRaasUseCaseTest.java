package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.exception.ListarArquivosRaasNotFoundException;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;
import br.gov.goiania.saude.raas.domain.service.ListarArquivosRaasDomainService;
import br.gov.goiania.saude.raas.mock.ListarArquivosRaasRequestMock;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarArquivosRaasUseCaseTest {

    @Mock
    private ListarArquivosRaasPort repositoryPort;

    @Mock
    private ListarArquivosRaasMapper mapper;

    private ListarArquivosRaasDomainService domainService;
    private ListarArquivosRaasUseCase useCase;

    @BeforeEach
    void configurar() {
        domainService = new ListarArquivosRaasDomainService();
        useCase = new ListarArquivosRaasUseCase(repositoryPort, domainService, mapper);
    }

    @Test
    void deveLancarExcecaoQuandoNenhumRaasEncontrado() {
        when(repositoryPort.execute(any(ListarArquivosRaasFiltro.class)))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> useCase.execute(ListarArquivosRaasRequestMock.notFound()))
                .isInstanceOf(ListarArquivosRaasNotFoundException.class)
                .hasMessageContaining("999");
    }
}
