package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.ports.out.DownloadArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.model.DownloadArquivosRaas;
import br.gov.goiania.saude.raas.infrastructure.mapper.DownloadArquivosRaasMapper;
import br.gov.goiania.saude.raas.mock.DownloadArquivosRaasMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DownloadArquivosRaasUseCaseTest {

    private final DownloadArquivosRaasPort port =
            mock(DownloadArquivosRaasPort.class);
    private final DownloadArquivosRaasMapper mapper =
            mock(DownloadArquivosRaasMapper.class);
    private final DownloadArquivosRaasUseCase useCase =
            new DownloadArquivosRaasUseCase(port, mapper);

    @BeforeEach
    void setup() {
        org.mockito.Mockito.reset(port, mapper);
    }

    @Test
    @DisplayName("Deve retornar arquivo quando ID é válido")
    void deveDowloadArquivoQuandoIdValido() {
        DownloadArquivosRaas dominio =
                DownloadArquivosRaasMock.valido();
        when(port.execute(any(Long.class))).thenReturn(dominio);
        when(mapper.toResponse(any())).thenReturn(null);
        assertDoesNotThrow(() -> useCase.execute(1L));
        verify(port).execute(any());
    }

    @Test
    @DisplayName("Deve propagar exceção quando port falha")
    void devePropagaExcecaoQuandoPortFalha() {
        when(port.execute(any(Long.class)))
                .thenThrow(new RuntimeException("Erro de banco"));
        assertThrows(RuntimeException.class,
                () -> useCase.execute(1L));
    }
}

