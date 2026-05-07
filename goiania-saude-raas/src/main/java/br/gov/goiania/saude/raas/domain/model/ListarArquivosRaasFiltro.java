package br.gov.goiania.saude.raas.domain.model;

public record ListarArquivosRaasFiltro(
        Integer mes,
        Integer ano,
        String codigoEmpresa,
        Integer status
) {

}

