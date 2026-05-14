package br.gov.goiania.saude.raas.infrastructure.config;

import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.application.usecase.ListarArquivosRaasUseCase;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    @Mock
    private ListarArquivosRaasPort repositoryPort;

    @Mock
    private ListarArquivosRaasMapper mapper;

    @InjectMocks
    private BeanConfiguration beanConfiguration;


    @Test
    @DisplayName("Deve criar o bean ListarArquivosRaasUseCasePort corretamente")
    void deveCriarBeanListarArquivosRaasUseCasePort() {
        ListarArquivosRaasUseCase useCase = (ListarArquivosRaasUseCase) beanConfiguration.listarRaasUseCase();
        assertNotNull(useCase);
        verifyNoInteractions(repositoryPort, mapper);
    }
}
