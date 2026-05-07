package br.gov.goiania.saude.raas.application.usecase;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.exception.ListarArquivosRaasNotFoundException;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;
import br.gov.goiania.saude.raas.domain.service.ListarArquivosRaasDomainService;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static br.gov.goiania.saude.raas.testutils.TestConstants.FIXTURE_LABEL_NOT_FOUND;
import static br.gov.goiania.saude.raas.testutils.TestConstants.FIXTURE_PATH;
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
        FixtureFactoryLoader.loadTemplates(FIXTURE_PATH);

        domainService = new ListarArquivosRaasDomainService();
        useCase = new ListarArquivosRaasUseCase(repositoryPort, domainService, mapper);
    }

    @Test
    void deveLancarExcecaoQuandoNenhumRaasEncontrado() {
        final ListarArquivosRaasRequest request = Fixture
                .from(ListarArquivosRaasRequest.class)
                .gimme(FIXTURE_LABEL_NOT_FOUND);

        when(repositoryPort.execute(any(ListarArquivosRaasFiltro.class)))
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(ListarArquivosRaasNotFoundException.class)
                .hasMessageContaining("999");
    }
}
