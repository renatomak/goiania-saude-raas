package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload;

import java.time.LocalDate;

public interface DownloadArquivosRaasProjection {

    Long getId();

    LocalDate getDtGeracao();

    String getTexto();
}


