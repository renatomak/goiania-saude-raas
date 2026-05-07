package br.gov.goiania.saude.raas.application.ports.in;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;

import java.util.List;

public interface ListarArquivosRaasUseCasePort {

    List<ListarArquivosRaasResponse> execute(ListarArquivosRaasRequest request);
}
