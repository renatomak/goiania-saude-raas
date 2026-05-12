package br.gov.goiania.saude.raas.infrastructure.adapter.web.interceptor;

import br.gov.goiania.saude.raas.infrastructure.config.LoggingInterceptor;
import br.gov.goiania.saude.raas.mock.HttpServletMock;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(MockitoExtension.class)
class LoggingInterceptorTest {

    @InjectMocks
    private LoggingInterceptor interceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("Deve adicionar correlationId e logar requisição com query string")
    void deveAdicionarCorrelationIdELogarRequisicaoComQueryString() {
        HttpServletRequest req = HttpServletMock.requestComQueryString("param=valor");
        HttpServletResponse resp = HttpServletMock.responseComStatus(200);
        boolean result = interceptor.preHandle(req, resp, new Object());
        String correlationId = MDC.get("correlationId");
        assertTrue(result);
        assertTrue(correlationId != null && correlationId.startsWith("ID-"));
        interceptor.afterCompletion(req, resp, new Object(), null);
        assertNull(MDC.get("correlationId"));
    }

    @Test
    @DisplayName("Deve adicionar correlationId e logar requisição sem query string")
    void deveAdicionarCorrelationIdELogarRequisicaoSemQueryString() {
        HttpServletRequest req = HttpServletMock.requestSemQueryString();
        HttpServletResponse resp = HttpServletMock.responseComStatus(201);
        boolean result = interceptor.preHandle(req, resp, new Object());
        String correlationId = MDC.get("correlationId");
        assertTrue(result);
        assertTrue(correlationId != null && correlationId.startsWith("ID-"));
        interceptor.afterCompletion(req, resp, new Object(), null);
        assertNull(MDC.get("correlationId"));
    }
}

