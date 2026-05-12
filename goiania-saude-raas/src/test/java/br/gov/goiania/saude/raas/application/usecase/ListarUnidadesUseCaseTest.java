package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.ListarUnidadesResponse;
import br.gov.goiania.saude.raas.application.ports.out.ListarUnidadesPort;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarUnidadesMapper;
import br.gov.goiania.saude.raas.mock.ListarUnidadesMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ListarUnidadesUseCaseTest {

    @Mock
    private ListarUnidadesPort port;

    @Mock
    private ListarUnidadesMapper mapper;

    private ListarUnidadesUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new ListarUnidadesUseCase(port, mapper);
    }

    @Test
    @DisplayName("Deve listar todas as unidades")
    void deveListarTodasAsUnidades() {
        var dominio = ListarUnidadesMock.valido();
        var response = new ListarUnidadesResponse(
                dominio.getId(),
                dominio.getNome()
        );

        when(port.execute()).thenReturn(List.of(dominio));
        when(mapper.toResponse(dominio)).thenReturn(response);

        List<ListarUnidadesResponse> resultado = useCase.execute();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(123L, resultado.get(0).id());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há unidades")
    void deveRetornarListaVaziaQuandoNaoHaUnidades() {
        when(port.execute()).thenReturn(List.of());

        List<ListarUnidadesResponse> resultado = useCase.execute();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
}

