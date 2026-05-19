package br.gov.goiania.saude.raas.application.dto;

import lombok.Data;

@Data
public class Header {
    private String competencia;
    private Long quantidadeFolhas;
    private Long campoControle;
    private String nomeResponsavel;
    private String siglaResponsavel;
    private String cnpjResponsavel;
    private String nomeDestino;
    private String dataGeracao;
    private String versaoSistema;
    private String versaoBdsia;
}
