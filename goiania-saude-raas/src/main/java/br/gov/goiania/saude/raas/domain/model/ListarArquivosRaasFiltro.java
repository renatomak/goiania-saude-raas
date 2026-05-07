package br.gov.goiania.saude.raas.domain.model;

import java.util.List;

public record ListarArquivosRaasFiltro(
        Integer mes,
        Integer ano,
        String codigoEmpresa,
        List<String> statusPermitidos
) {

    public ListarArquivosRaasFiltro {
        if (mes == null || mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mes deve estar entre 1 e 12");
        }
        if (ano == null || ano < 2000) {
            throw new IllegalArgumentException("Ano invalido");
        }
        if (codigoEmpresa == null || codigoEmpresa.isBlank()) {
            throw new IllegalArgumentException("Codigo da empresa e obrigatorio");
        }
        if (statusPermitidos == null || statusPermitidos.isEmpty()) {
            throw new IllegalArgumentException("Ao menos um status deve ser informado");
        }
    }
}
