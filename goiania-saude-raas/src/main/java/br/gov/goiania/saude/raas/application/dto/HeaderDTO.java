package br.gov.goiania.saude.raas.application.dto;

import lombok.Data;

@Data
public class HeaderDTO {
    private String competencia;
    private String nomeResponsavel;
    private String siglaResponsavel;
    private String cnpjResponsavel;
    private String nomeDestino;
    private String dataGeracao;
    private String versaoSistema;
    private String versaoBdsia;
}
