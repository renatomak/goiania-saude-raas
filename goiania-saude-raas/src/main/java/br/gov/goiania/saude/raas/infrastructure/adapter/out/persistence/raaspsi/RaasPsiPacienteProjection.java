package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import java.time.LocalDate;

public interface RaasPsiPacienteProjection {

    Long getCdRaasPsi();

    Integer getUnidadeFederacao();

    LocalDate getCompetencia();

    Integer getUnidadePrestadoraServico();

    String getCartaoNacionalSaude();

    LocalDate getDtInicioValidade();

    LocalDate getDtFinalValidade();

    String getNmPaciente();

    Integer getNumeroProntuario();

    String getNmMae();

    String getLogradouro();

    String getNumeroLogradouro();

    String getComplementoLogradouro();

    String getCep();

    Integer getMunicipio();

    LocalDate getDtNascimento();

    String getSexo();

    Integer getRaca();

    String getNmResponsavel();

    Integer getNacionalidade();

    Integer getEtnia();

    String getTelefone();

    String getCelular();

    Integer getMotivoSaidaPermanencia();

    LocalDate getDtOcorrencia();

    String getCidPrincipal();

    String getCidSecundario1();

    String getCidSecundario2();

    String getCidSecundario3();

    String getCidCausasAssociadas();

    Integer getCaraterAtendimento();

    Integer getOrigemPaciente();

    String getCoberturaEsf();

    Integer getCodigoCoberturaEsf();

    Integer getTotalProcedimentos();

    Integer getDestinoPaciente();

    String getOrigemInformacoes();

    String getSituacaoRua();

    String getUsuarioDrogas();

    String getTipoDrogaAlcool();

    String getTipoDrogaCrack();

    String getTipoDrogaOutros();

    Long getNumeroAutorizacao();

    String getDescricaoBairro();

    Integer getTipoLogradouro();

    String getEmailPaciente();

    String getCpfPaciente();
}
