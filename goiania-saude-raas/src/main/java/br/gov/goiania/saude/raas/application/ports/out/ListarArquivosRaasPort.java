package br.gov.goiania.saude.raas.application.ports.out;

import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarArquivosRaasPort {

    Page<ListarArquivosRaas> execute(ListarArquivosRaasFiltro filtro, Pageable pageable);
}
