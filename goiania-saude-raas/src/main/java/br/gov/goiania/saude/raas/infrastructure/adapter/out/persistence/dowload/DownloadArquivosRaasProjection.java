
package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload;


public interface DownloadArquivosRaasProjection {

    Long getId();

    java.time.LocalDate getDataGeracao();

    String getNome();

    String getArquivo();
}


