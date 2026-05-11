package br.gov.goiania.saude.raas.infrastructure.adapter.in.web;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.DownloadArquivosRaasUseCasePort;
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

class DownloadArquivosRaasControllerTest {

    @Mock
    private DownloadArquivosRaasUseCasePort useCasePort;

    private DownloadArquivosRaasController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new DownloadArquivosRaasController(useCasePort);
    }

    @Test
    @DisplayName("Deve retornar arquivo quando ID é válido")
    void deveRetornarArquivoQuandoIdValido() {

        DownloadArquivosRaasResponse response =
                DownloadArquivosRaasResponseMock.valido();

        when(useCasePort.execute(any(Long.class))).thenReturn(response);

        ResponseEntity<DownloadArquivosRaasResponse> result = controller.download(1L);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(response, result.getBody());
    }
}

