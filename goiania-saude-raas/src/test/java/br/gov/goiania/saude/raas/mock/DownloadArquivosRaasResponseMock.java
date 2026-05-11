package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;

import java.time.LocalDate;

public final class DownloadArquivosRaasResponseMock {

    private DownloadArquivosRaasResponseMock() { }

    public static DownloadArquivosRaasResponse valido() {
        return new DownloadArquivosRaasResponse(
                1L,
                LocalDate.of(2026, 5, 7),
                "/raas/arquivo_2025040018831729.txt",
                "01#RAS#2025040018831729SECRETARIA MUNICIPAL DE SAÚDE 52087025141524000123SECRETARIA MUNICIPAL DE SAÚDE DE GOIANIAM20\n"
        );
    }
}

