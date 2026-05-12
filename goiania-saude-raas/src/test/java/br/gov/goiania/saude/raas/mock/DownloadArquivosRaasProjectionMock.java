package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload.DownloadArquivosRaasProjection;
import java.time.LocalDate;

public final class DownloadArquivosRaasProjectionMock {

    private DownloadArquivosRaasProjectionMock() { }

    public static DownloadArquivosRaasProjection valido() {
        return new ProjectionValida();
    }

    private static class ProjectionValida implements DownloadArquivosRaasProjection {
        @Override
        public Long getId() {
            return 1L;
        }

        @Override
        public LocalDate getDataGeracao() {
            return LocalDate.of(2026, 5, 7);
        }

        @Override
        public String getNome() {
            return "/raas/arquivo_2025040018831729.txt";
        }

        @Override
        public String getArquivo() {
            return "01#RAS#2025040018831729SECRETARIA MUNICIPAL DE SAÚDE 52087025141524000123SECRETARIA MUNICIPAL DE SAÚDE DE GOIANIAM20\n";
        }
    }
}
