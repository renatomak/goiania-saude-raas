package br.gov.goiania.saude.raas.infrastructure.adapter.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String CORRELATION_ID_KEY = "correlationId";
    private static final String START_TIME_KEY = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String correlationId = "ID-" + UUID.randomUUID().toString().substring(0, 5);
        MDC.put(CORRELATION_ID_KEY, correlationId);
        request.setAttribute(START_TIME_KEY, System.currentTimeMillis());

        String queryString = request.getQueryString();
        if (log.isInfoEnabled()) {
            log.info("Recebendo {} {}{}{} | Params: {}",
                request.getMethod(),
                request.getRequestURI(),
                queryString != null && !queryString.isEmpty() ? "?" : "",
                queryString != null && !queryString.isEmpty() ? queryString : "",
                queryString != null && !queryString.isEmpty() ? queryString : "-");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_KEY);
        long duration = startTime != null ? System.currentTimeMillis() - startTime : -1;
        if (log.isInfoEnabled()) {
            log.info("Finalizado em {}ms | Status: {}", duration, response.getStatus());
        }
        MDC.remove(CORRELATION_ID_KEY);
    }
}
