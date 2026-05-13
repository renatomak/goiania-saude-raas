package br.gov.goiania.saude.raas.infrastructure.adapter.in.web;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.DownloadArquivosRaasPortIn;
import br.gov.goiania.saude.raas.mock.DownloadArquivosRaasResponseMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DownloadArquivosRaasControllerIntegrationTest {

    @Mock
    private DownloadArquivosRaasPortIn useCasePort;

    private DownloadArquivosRaasController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new DownloadArquivosRaasController(useCasePort);
    }

    @Test
    @DisplayName("Deve retornar 200 ao fazer download com ID válido")
    void deveRetornar200AoFazerDownloadComIdValido() {
        DownloadArquivosRaasResponse response =
                DownloadArquivosRaasResponseMock.valido();

        when(useCasePort.execute(any(Long.class))).thenReturn(response);

        ResponseEntity<DownloadArquivosRaasResponse> resultado =
                controller.download(1L);

        assertEquals(200, resultado.getStatusCode().value());
        assertNotNull(resultado.getBody());
    }

    @Test
    @DisplayName("Deve retornar dados completos do arquivo")
    void deveRetornarDadosCompletosDoArquivo() {
        DownloadArquivosRaasResponse response =
                DownloadArquivosRaasResponseMock.valido();

        when(useCasePort.execute(any(Long.class))).thenReturn(response);

        ResponseEntity<DownloadArquivosRaasResponse> resultado =
                controller.download(1L);

        assertEquals(200, resultado.getStatusCode().value());
        assertNotNull(resultado.getBody());
        assertNotNull(resultado.getBody().id());
        assertNotNull(resultado.getBody().nome());
        assertNotNull(resultado.getBody().arquivo());
        assertNotNull(resultado.getBody().dataGeracao());
    }
}

