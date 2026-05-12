package br.gov.goiania.saude.raas.infrastructure.adapter.in.web;

import br.gov.goiania.saude.raas.application.dto.ListarUnidadesResponse;
import br.gov.goiania.saude.raas.application.ports.in.ListarUnidadesUseCasePort;
import br.gov.goiania.saude.raas.mock.ListarUnidadesResponseMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ListarUnidadesControllerTest {

    @Mock
    private ListarUnidadesUseCasePort useCasePort;

    private ListarUnidadesController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ListarUnidadesController(useCasePort);
    }

    @Test
    @DisplayName("Deve retornar lista de unidades com status 200")
    void deveRetornarListaDeUnidadesComStatus200() {
        List<ListarUnidadesResponse> lista = ListarUnidadesResponseMock.listaPopular();
        when(useCasePort.execute()).thenReturn(lista);

        ResponseEntity<List<ListarUnidadesResponse>> resultado =
                controller.listarTodasAsUnidades();

        assertEquals(200, resultado.getStatusCode().value());
        assertNotNull(resultado.getBody());
        assertEquals(3, resultado.getBody().size());
    }
}

