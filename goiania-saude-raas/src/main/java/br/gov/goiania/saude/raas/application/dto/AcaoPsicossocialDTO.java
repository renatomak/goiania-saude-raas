package br.gov.goiania.saude.raas.application.dto;

import lombok.Data;

@Data
public class AcaoPsicossocialDTO {
    private String procedimento;
    private String cbo;
    private String cnsProfissional;
    private String dataExecucao;
    private String classificacao;
    private Integer quantidade;
    private String servico;
    private String localRealizacao;
    private String origemInformacoes;
}
