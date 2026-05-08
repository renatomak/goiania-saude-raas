package br.gov.goiania.saude.raas.application.ports.out;

import br.gov.goiania.saude.raas.domain.model.DownloadArquivosRaas;

public interface DownloadArquivosRaasPort {

    DownloadArquivosRaas execute(Long id);
}
