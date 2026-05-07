package br.gov.goiania.saude.raas.application.dto;

public record ListarArquivosRaasRequest(
        Integer mes,
        Integer ano,
        String codigoEmpresa,
        Integer situacao
) {
}
