package br.gov.goiania.saude.raas.application.ports.out;

import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;

import java.util.List;

public interface ListarArquivosRaasPort {

    List<ListarArquivosRaas> execute(ListarArquivosRaasFiltro filtro);
}
