package br.gov.goiania.saude.raas.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ListarArquivosRaasResponse(
        Integer mes,
        Integer ano,
        LocalDate dataGeracao,
        String codigoEmpresa,
        String nomeEmpresa,
        String descricaoProcesso,
        String path,
        String status,
        BigDecimal totalFolha
) {
}
