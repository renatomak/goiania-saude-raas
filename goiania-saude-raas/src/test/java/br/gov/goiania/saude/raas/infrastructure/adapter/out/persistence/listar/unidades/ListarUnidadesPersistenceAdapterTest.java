package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.unidades;

import br.gov.goiania.saude.raas.application.ports.out.ListarUnidadesPort;
import br.gov.goiania.saude.raas.domain.model.ListarUnidades;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarUnidadesMapper;
import br.gov.goiania.saude.raas.mock.ListarUnidadesProjectionMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ListarUnidadesPersistenceAdapterTest {

    @Mock
    private ListarUnidadesRepository repository;

    @Mock
    private ListarUnidadesMapper mapper;

    private ListarUnidadesPort adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new ListarUnidadesPersistenceAdapter(repository, mapper);
    }

    @Test
    @DisplayName("Deve listar todas as unidades do repositório")
    void deveExecute() {
        var projection = ListarUnidadesProjectionMock.valido();

        when(repository.listarTodasAsUnidades()).thenReturn(List.of(projection));

        List<ListarUnidadesProjection> resultado = repository.listarTodasAsUnidades();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há unidades")
    void deveRetornarListaVaziaQuandoNaoHaUnidades() {
        when(repository.listarTodasAsUnidades()).thenReturn(List.of());

        List<ListarUnidadesProjection> resultado = repository.listarTodasAsUnidades();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    @DisplayName("Deve mapear corretamente a projeção para domínio")
    void deveMapearCorretamenteAProjecaoParaDominio() {
        var projection = ListarUnidadesProjectionMock.valido();

        when(repository.listarTodasAsUnidades()).thenReturn(List.of(projection));
        when(mapper.toDomain(projection)).thenReturn(
                new ListarUnidades(
                        123L,
                        "Secretaria Municipal de Saúde"
                )
        );

        List<br.gov.goiania.saude.raas.domain.model.ListarUnidades> resultado =
                adapter.execute();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(123L, resultado.get(0).getId());
        assertEquals("Secretaria Municipal de Saúde", resultado.get(0).getNome());
    }
}

