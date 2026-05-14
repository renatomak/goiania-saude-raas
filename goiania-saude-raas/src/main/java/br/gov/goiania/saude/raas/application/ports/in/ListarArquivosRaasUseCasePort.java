package br.gov.goiania.saude.raas.application.ports.in;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;
import org.springframework.data.domain.Page;

public interface ListarArquivosRaasUseCasePort {

    Page<ListarArquivosRaasResponse> execute(ListarArquivosRaasRequest request);
}
