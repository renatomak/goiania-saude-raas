package br.gov.goiania.saude.raas.application.dto;

import java.util.List;
import lombok.Data;

@Data
public class PacientePsicossocialDTO {
    private String uf;
    private String competencia;
    private String cnes;
    private String cnsPaciente;
    private String cpfPaciente;
    private String dataInicio;
    private String dataFim;
    private String nomePaciente;
    private String nomeMae;
    private String logradouro;
    private String numeroEndereco;
    private String complemento;
    private String cep;
    private String municipioIbge;
    private String dataNascimento;
    private String sexo;
    private String racaCor;
    private String nomeResponsavel;
    private String etnia;
    private String celular;
    private String telefone;
    private String motivoSaida;
    private String cidPrincipal;
    private String coberturaEsf;
    private String cnesEsf;
    private String destinoPaciente;
    private List<AcaoPsicossocialDTO> acoes;
}
