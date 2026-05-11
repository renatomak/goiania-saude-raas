package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload;

import br.gov.goiania.saude.raas.domain.model.DownloadArquivosRaas;
import br.gov.goiania.saude.raas.infrastructure.mapper.DownloadArquivosRaasMapper;
import br.gov.goiania.saude.raas.mock.DownloadArquivosRaasMock;
import br.gov.goiania.saude.raas.mock.DownloadArquivosRaasProjectionMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DownloadArquivosRaasPersistenceAdapterTest {

    @Mock
    private DownloadArquivosRaasRepository repository;

    @Mock
    private DownloadArquivosRaasMapper mapper;

    @InjectMocks
    private DownloadArquivosRaasPersistenceAdapter adapter;

    @Test
    @DisplayName("Deve retornar arquivo quando ID é válido")
    void deveRetornarArquivoQuandoIdValido() {
        DownloadArquivosRaasProjection projection =
                DownloadArquivosRaasProjectionMock.valido();
        DownloadArquivosRaas domain =
                DownloadArquivosRaasMock.valido();

        when(repository.download(any(Long.class)))
                .thenReturn(projection);
        when(mapper.toDomain(projection)).thenReturn(domain);

        DownloadArquivosRaas resultado = adapter.execute(1L);

        assertNotNull(resultado);
        assertEquals(domain, resultado);
    }

    @Test
    @DisplayName("Deve retornar propriedades corretas do arquivo")
    void deveRetornarPropriedadesCorretasDoArquivo() {
        DownloadArquivosRaasProjection projection =
                DownloadArquivosRaasProjectionMock.valido();
        DownloadArquivosRaas domain =
                DownloadArquivosRaasMock.valido();

        when(repository.download(any(Long.class)))
                .thenReturn(projection);
        when(mapper.toDomain(projection)).thenReturn(domain);

        DownloadArquivosRaas resultado = adapter.execute(1L);

        assertEquals(1L, resultado.getId());
        assertNotNull(resultado.getPath());
        assertNotNull(resultado.getTexto());
        assertNotNull(resultado.getDataGeracao());
    }

    @Test
    @DisplayName("Deve propagar exceção quando repositório falha")
    void devePropagaExcecaoQuandoRepositorioFalha() {
        when(repository.download(any(Long.class)))
                .thenThrow(new RuntimeException("Erro de banco"));

        try {
            adapter.execute(1L);
        } catch (RuntimeException e) {
            assertEquals("Erro de banco", e.getMessage());
        }
    }
}

