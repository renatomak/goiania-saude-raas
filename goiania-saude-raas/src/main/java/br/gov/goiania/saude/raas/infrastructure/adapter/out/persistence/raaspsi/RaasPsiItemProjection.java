package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import java.time.LocalDate;

public interface RaasPsiItemProjection {

    Long getCdRaasPsi();

    Integer getUnidadeFederacao();

    LocalDate getCompetencia();

    Integer getUnidadePrestadoraServico();

    String getCartaoNacionalSaude();

    LocalDate getDtInicioValidade();

    Long getCodProcedimento();

    String getCodCboExecutante();

    String getCnsExecutante();

    LocalDate getDtExecucaoProcedimento();

    Integer getServico();

    Integer getClassificacao();

    Integer getQuantidadeRealizada();

    String getOrigemInformacoes();

    String getLocalRealizacao();

    String getCpfPaciente();
}
