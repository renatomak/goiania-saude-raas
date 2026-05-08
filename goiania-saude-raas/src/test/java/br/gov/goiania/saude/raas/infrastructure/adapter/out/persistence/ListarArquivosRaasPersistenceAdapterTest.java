package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence;

import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;
import br.gov.goiania.saude.raas.mock.ListarArquivosRaasEntityMock;
import br.gov.goiania.saude.raas.mock.ListarArquivosRaasFiltroMock;
import br.gov.goiania.saude.raas.mock.ListarArquivosRaasMock;
import br.gov.goiania.saude.raas.mock.ListarArquivosRaasProjectionMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class ListarArquivosRaasPersistenceAdapterTest {

    @Mock
    private ListarArquivosRaasRepository repository;

    @Mock
    private ListarArquivosRaasMapper mapper;

    private ListarArquivosRaasFiltro filtroMock;

    @InjectMocks
    private ListarArquivosRaasPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        filtroMock = ListarArquivosRaasFiltroMock.valido();
    }

    @Test
    @DisplayName("Deve retornar lista de arquivos quando filtros válidos")
    void deveRetornarListaDeArquivosQuandoFiltrosValidos() {
        ListarArquivosRaasProjection projection = ListarArquivosRaasProjectionMock.valido();
        ListarArquivosRaas domain = ListarArquivosRaasMock.valido();
        when(repository.buscarProcessosPorCompetenciaEmpresaEStatus(any(), any(), any(), any())).thenReturn(List.of(projection));
        when(mapper.toDomain(projection)).thenReturn(domain);
        List<ListarArquivosRaas> resultado = adapter.execute(filtroMock);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(domain, resultado.get(0));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando repositório não encontra dados")
    void deveRetornarListaVaziaQuandoRepositorioNaoEncontraDados() {
        when(repository.buscarProcessosPorCompetenciaEmpresaEStatus(any(), any(), any(), any())).thenReturn(List.of());
        List<ListarArquivosRaas> resultado = adapter.execute(filtroMock);
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    @DisplayName("Deve criar e acessar todos os campos de ListarArquivosRaasEntity")
    void deveCriarEAcessarTodosOsCamposDeListarArquivosRaasEntity() {
        ListarArquivosRaasEntity entity = ListarArquivosRaasEntityMock.exemplo();
        assertEquals(2L, entity.getId());
        assertEquals(6, entity.getMes());
        assertEquals(2027, entity.getAno());
        assertEquals(LocalDate.of(2027, 6, 8), entity.getDataGeracao());
        assertEquals("456", entity.getCodigoEmpresa());
        assertEquals("Empresa Exemplo", entity.getNomeEmpresa());
        assertEquals("/outro/caminho/arquivo.txt", entity.getPath());
        assertEquals("4", entity.getStatus());
        assertEquals(new BigDecimal("200.00"), entity.getTotalFolha());
    }
}
