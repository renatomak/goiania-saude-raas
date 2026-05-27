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
    private String numeroProntuario;
    private String origemPaciente;
    private String situacaoRua;
    private String usuarioDrogas;
    private String tipoDrogaAlcool;
    private String tipoDrogaCrack;
    private String tipoDrogaOutros;
    private String numeroAutorizacao;
    private String descricaoBairro;
    private String tipoLogradouro;
    private String emailPaciente;
    private String nacionalidade;
    private String dtOcorrencia;
    private String cidSecundario1;
    private String cidSecundario2;
    private String cidSecundario3;
    private String cidCausasAssociadas;
    private String caraterAtendimento;
    private Integer totalProcedimentos;
    private String origemInformacoes;
    private List<AcaoPsicossocialDTO> acoes;
}
