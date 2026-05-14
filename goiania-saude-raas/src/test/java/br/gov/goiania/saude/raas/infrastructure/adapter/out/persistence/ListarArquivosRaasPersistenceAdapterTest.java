package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence;

import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.arquivos.ListarArquivosRaasPersistenceAdapter;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.arquivos.ListarArquivosRaasProjection;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.arquivos.ListarArquivosRaasRepository;
import br.gov.goiania.saude.raas.mock.ArquivosRaasEntityMock;
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
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ListarArquivosRaasPersistenceAdapterTest {

    @Mock
    private ListarArquivosRaasRepository repository;

    @Mock
    private ListarArquivosRaasMapper mapper;

    private ListarArquivosRaasFiltro filtroMock;

    @InjectMocks
    private ListarArquivosRaasPersistenceAdapter adapter;

    private static final Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        filtroMock = ListarArquivosRaasFiltroMock.valido();
    }

    @Test
    @DisplayName("Deve retornar lista de arquivos quando filtros válidos")
    void deveRetornarListaDeArquivosQuandoFiltrosValidos() {
        ListarArquivosRaasProjection projection = ListarArquivosRaasProjectionMock.valido();
        ListarArquivosRaas domain = ListarArquivosRaasMock.valido();
        when(repository.buscarProcessosPorCompetenciaEmpresaEStatus(
                any(), any(), any(), any(), any()
        )).thenReturn(new PageImpl<>(List.of(projection)));
        when(mapper.toDomain(projection)).thenReturn(domain);
        Page<ListarArquivosRaas> resultado = adapter.execute(filtroMock, pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals(domain, resultado.getContent().get(0));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando repositório não encontra dados")
    void deveRetornarListaVaziaQuandoRepositorioNaoEncontraDados() {
        when(repository.buscarProcessosPorCompetenciaEmpresaEStatus(
                any(), any(), any(), any(), any()
        )).thenReturn(new PageImpl<>(List.of()));
        Page<ListarArquivosRaas> resultado = adapter.execute(filtroMock, pageable);
        assertNotNull(resultado);
        assertEquals(0, resultado.getTotalElements());
    }

    @Test
    @DisplayName("Deve criar e acessar todos os campos de ListarArquivosRaasEntity")
    void deveCriarEAcessarTodosOsCamposDeListarArquivosRaasEntity() {
        ArquivosRaasEntity entity = ArquivosRaasEntityMock.exemplo();
        assertEquals(2L, entity.getId());
        assertEquals(6, entity.getMes());
        assertEquals(2027, entity.getAno());
        assertEquals(LocalDate.of(2027, 6, 8), entity.getDataGeracao());
        assertEquals("456", entity.getCodigoEmpresa());
        assertEquals("Empresa Exemplo", entity.getNomeEmpresa());
        assertEquals("/outro/caminho/arquivo.txt", entity.getPath());
        assertEquals("4", entity.getStatus());
        assertEquals(new BigDecimal("200.00"), entity.getTotalFolha());
        assertEquals("textoExemplo", entity.getTexto());
    }

    @Test
    @DisplayName("Deve criar instância com construtor protegido")
    void deveCriarInstanciaComConstrutorProtegido() {
        ArquivosRaasEntity entity = new ArquivosRaasEntity();
        assertNotNull(entity);
    }
}
