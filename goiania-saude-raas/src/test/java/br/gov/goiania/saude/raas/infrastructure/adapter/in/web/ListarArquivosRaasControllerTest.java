package br.gov.goiania.saude.raas.infrastructure.adapter.in.web;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.ListarArquivosRaasUseCasePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        ListarArquivosRaasResponse response = new ListarArquivosRaasResponse(5, 2026, LocalDate.of(2026, 5, 7), "123", "Empresa Teste", "/caminho/arquivo.txt", "3", new BigDecimal("100.00"));
        when(useCasePort.execute(any(ListarArquivosRaasRequest.class))).thenReturn(List.of(response));
        ResponseEntity<List<ListarArquivosRaasResponse>> result = controller.listarRaas(5, 2026, "123", 3);
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("123", result.getBody().get(0).codigoEmpresa());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver resultados")
    void deveRetornarListaVaziaQuandoSemResultados() {
        when(useCasePort.execute(any(ListarArquivosRaasRequest.class))).thenReturn(Collections.emptyList());
        ResponseEntity<List<ListarArquivosRaasResponse>> result = controller.listarRaas(null, null, null, null);
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
    }
}

