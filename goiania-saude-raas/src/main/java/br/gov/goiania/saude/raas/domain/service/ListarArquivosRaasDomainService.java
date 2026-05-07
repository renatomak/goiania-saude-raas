package br.gov.goiania.saude.raas.domain.service;

import br.gov.goiania.saude.raas.domain.exception.ListarArquivosRaasNotFoundException;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;

import java.util.List;

public class ListarArquivosRaasDomainService {

    public List<ListarArquivosRaas> validarERetornarResultados(
            final List<ListarArquivosRaas> registros,
            final ListarArquivosRaasFiltro filtro) {
        if (registros.isEmpty()) {
            throw new ListarArquivosRaasNotFoundException(
                    filtro.codigoEmpresa(),
                    filtro.mes(),
                    filtro.ano());
        }
        return registros;
    }
}
