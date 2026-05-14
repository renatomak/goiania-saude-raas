package br.gov.goiania.saude.raas.infrastructure.adapter.in.web;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.ListarArquivosRaasUseCasePort;
import br.gov.goiania.saude.raas.mock.ListarArquivosRaasResponseMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ListarArquivosRaasControllerTest {

    @Mock
    private ListarArquivosRaasUseCasePort useCasePort;

    private ListarArquivosRaasController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ListarArquivosRaasController(useCasePort);
    }

    @Test
    @DisplayName("Deve retornar lista de arquivos quando filtros são válidos")
    void deveRetornarListaDeArquivosQuandoFiltrosValidos() {
        ListarArquivosRaasResponse response = ListarArquivosRaasResponseMock.valido();
        Page<ListarArquivosRaasResponse> page = new PageImpl<>(List.of(response));
        when(useCasePort.execute(any(ListarArquivosRaasRequest.class))).thenReturn(page);
        var result = controller.listarRaas(5, 2026, "123", 3, 0, 10);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getContent().size());
        assertEquals(response, result.getBody().getContent().get(0));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver resultados")
    void deveRetornarListaVaziaQuandoSemResultados() {
        Page<ListarArquivosRaasResponse> emptyPage = Page.empty();
        when(useCasePort.execute(any(ListarArquivosRaasRequest.class))).thenReturn(emptyPage);
        ResponseEntity<Page<ListarArquivosRaasResponse>> result = controller.listarRaas(null, null, null, null, 0, 10);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
    }
}
