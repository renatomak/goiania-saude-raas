package br.gov.goiania.saude.raas.infrastructure.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import java.util.List;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoggingAspectTest {

    @InjectMocks
    private LoggingAspect aspect;

    @Mock
    private Logger log;

    @Test
    @DisplayName("Deve logar execução de método com retorno Collection")
    void deveLogarExecucaoMetodoComRetornoCollection() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        MethodSignature signature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("metodoTeste");
        lenient().when(signature.getDeclaringType()).thenReturn(List.class);
        when(log.isInfoEnabled()).thenReturn(true);
        List<String> resultado = List.of("a", "b");
        when(joinPoint.proceed()).thenReturn(resultado);
        Object retorno = aspect.logExecution(joinPoint);
        assertNotNull(retorno);
        assertEquals(2, ((Collection<?>) retorno).size());
    }

    @Test
    @DisplayName("Deve logar execução de método com retorno não Collection")
    void deveLogarExecucaoMetodoComRetornoNaoCollection() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        MethodSignature signature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("metodoTeste");
        lenient().when(signature.getDeclaringType()).thenReturn(String.class);
        when(log.isInfoEnabled()).thenReturn(true);
        when(joinPoint.proceed()).thenReturn("resultado");
        Object retorno = aspect.logExecution(joinPoint);
        assertNotNull(retorno);
        assertEquals("resultado", retorno);
    }
}
