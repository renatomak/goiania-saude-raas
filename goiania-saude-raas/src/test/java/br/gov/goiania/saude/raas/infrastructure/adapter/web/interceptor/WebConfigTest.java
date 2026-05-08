package br.gov.goiania.saude.raas.infrastructure.adapter.web.interceptor;

import br.gov.goiania.saude.raas.mock.InterceptorRegistryMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WebConfigTest {

    @Mock
    private LoggingInterceptor loggingInterceptor;

    @InjectMocks
    private WebConfig webConfig;

    @Test
    @DisplayName("Deve registrar o LoggingInterceptor para o path /v1/**")
    void deveRegistrarLoggingInterceptorParaPathV1() {
        InterceptorRegistry registry = InterceptorRegistryMock.registryComRegistration(loggingInterceptor);
        webConfig.addInterceptors(registry);
        verify(registry).addInterceptor(loggingInterceptor);
        assertNotNull(webConfig);
    }
}
