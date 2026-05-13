package br.gov.goiania.saude.raas.application.ports.in;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;

public interface DownloadArquivosRaasPortIn {

    DownloadArquivosRaasResponse execute(Long id);
}
