package br.gov.goiania.saude.raas.infrastructure.adapter.web.interceptor;

import br.gov.goiania.saude.raas.infrastructure.config.LoggingInterceptor;
import br.gov.goiania.saude.raas.infrastructure.config.WebConfig;
import br.gov.goiania.saude.raas.mock.InterceptorRegistryMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    @DisplayName("Deve registrar configuracao CORS global")
    void deveRegistrarConfiguracaoCorsGlobal() {
        CorsRegistry registry = org.mockito.Mockito.mock(CorsRegistry.class);
        CorsRegistration registration = org.mockito.Mockito.mock(CorsRegistration.class);
        when(registry.addMapping("/**")).thenReturn(registration);
        when(registration.allowedOriginPatterns(any(String[].class))).thenReturn(registration);
        when(registration.allowedMethods(any(String[].class))).thenReturn(registration);
        when(registration.allowedHeaders(any(String[].class))).thenReturn(registration);
        when(registration.exposedHeaders(any(String[].class))).thenReturn(registration);
        when(registration.allowCredentials(anyBoolean())).thenReturn(registration);
        when(registration.maxAge(anyLong())).thenReturn(registration);

        webConfig.addCorsMappings(registry);

        verify(registry).addMapping("/**");
        verify(registration).allowedOriginPatterns("*");
    }
}
