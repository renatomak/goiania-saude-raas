package br.gov.goiania.saude.raas.infrastructure.config;

import br.gov.goiania.saude.raas.domain.exception.ListarArquivosRaasNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;

import java.net.URI;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Deve tratar ListarArquivosRaasNotFoundException corretamente")
    void deveTratarListarArquivosRaasNotFoundException() {
        ListarArquivosRaasNotFoundException ex = new ListarArquivosRaasNotFoundException("123", 5, 2026);
        WebRequest request = mock(WebRequest.class);
        ProblemDetail detail = handler.handleRaasNotFound(ex, request);
        assertNotNull(detail);
        assertEquals(HttpStatus.NOT_FOUND.value(), detail.getStatus());
        assertEquals("RAAS Não Encontrado", detail.getTitle());
        assertEquals("Nenhum RAAS encontrado para empresa 123 na competencia 05/2026", detail.getDetail());
        assertEquals(URI.create("https://raas.saude.goiania.go.gov.br/problems/raas-nao-encontrado"), detail.getType());
        assertNotNull(detail.getProperties());
        assertNotNull(detail.getProperties().get("timestamp"));
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentNotValidException corretamente")
    void deveTratarMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("obj", "campo", "erro");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        when(ex.getBindingResult()).thenReturn(bindingResult);
        ProblemDetail detail = handler.handleValidationError(ex);
        assertNotNull(detail);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), detail.getStatus());
        assertEquals("Dados de Requisição Inválidos", detail.getTitle());
        assertEquals("campo: erro", detail.getDetail());
        assertEquals(URI.create("https://raas.saude.goiania.go.gov.br/problems/dados-invalidos"), detail.getType());
        assertNotNull(detail.getProperties());
        assertNotNull(detail.getProperties().get("timestamp"));
    }

    @Test
    @DisplayName("Deve tratar IllegalArgumentException corretamente")
    void deveTratarIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido");
        ProblemDetail detail = handler.handleIllegalArgument(ex);
        assertNotNull(detail);
        assertEquals(HttpStatus.BAD_REQUEST.value(), detail.getStatus());
        assertEquals("Argumento Inválido", detail.getTitle());
        assertEquals("Argumento inválido", detail.getDetail());
        assertEquals(URI.create("https://raas.saude.goiania.go.gov.br/problems/argumento-invalido"), detail.getType());
        assertNotNull(detail.getProperties());
        assertNotNull(detail.getProperties().get("timestamp"));
    }

    @Test
    @DisplayName("Deve tratar Exception genérica corretamente")
    void deveTratarExceptionGenerica() {
        Exception ex = new Exception("Falha interna");
        ProblemDetail detail = handler.handleGenericError(ex);
        assertNotNull(detail);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), detail.getStatus());
        assertEquals("Erro Interno do Servidor", detail.getTitle());
        assertEquals("Ocorreu um erro inesperado. Contate o suporte.", detail.getDetail());
        assertEquals(URI.create("https://raas.saude.goiania.go.gov.br/problems/erro-interno"), detail.getType());
        assertNotNull(detail.getProperties());
        assertNotNull(detail.getProperties().get("timestamp"));
    }
}
