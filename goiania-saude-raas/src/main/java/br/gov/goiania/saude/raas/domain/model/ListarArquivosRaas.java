package br.gov.goiania.saude.raas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ListarArquivosRaas {

    private final Integer mes;
    private final Integer ano;
    private final LocalDate dataGeracao;
    private final String codigoEmpresa;
    private final String nomeEmpresa;
    private final String path;
    private final String status;
    private final BigDecimal totalFolha;
}
