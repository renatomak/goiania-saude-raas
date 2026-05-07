package br.gov.goiania.saude.raas.infrastructure.config;

import br.gov.goiania.saude.raas.domain.exception.ListarArquivosRaasNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP_PROPERTY = "timestamp";
    private static final String BASE_TYPE_URI = "https://raas.saude.goiania.go.gov.br/problems/";

    @ExceptionHandler(ListarArquivosRaasNotFoundException.class)
    public ProblemDetail handleRaasNotFound(
            final ListarArquivosRaasNotFoundException ex,
            final WebRequest request) {

        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create(BASE_TYPE_URI + "raas-nao-encontrado"));
        problem.setTitle("RAAS Não Encontrado");
        problem.setProperty(TIMESTAMP_PROPERTY, Instant.now());
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationError(
            final MethodArgumentNotValidException ex) {

        final String detalhes = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY, detalhes);
        problem.setType(URI.create(BASE_TYPE_URI + "dados-invalidos"));
        problem.setTitle("Dados de Requisição Inválidos");
        problem.setProperty(TIMESTAMP_PROPERTY, Instant.now());
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(
            final IllegalArgumentException ex) {

        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setType(URI.create(BASE_TYPE_URI + "argumento-invalido"));
        problem.setTitle("Argumento Inválido");
        problem.setProperty(TIMESTAMP_PROPERTY, Instant.now());
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericError(final Exception ex) {
        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado. Contate o suporte.");
        problem.setType(URI.create(BASE_TYPE_URI + "erro-interno"));
        problem.setTitle("Erro Interno do Servidor");
        problem.setProperty(TIMESTAMP_PROPERTY, Instant.now());
        return problem;
    }
}
