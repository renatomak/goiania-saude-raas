package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import java.time.LocalDate;

public interface RaasHeaderProjection {

    Long getQuantidadeFolhas();

    Long getCampoControle();

    String getNmOrgaoOrigem();

    String getSiglaOrgaoOrigem();

    Long getCgcPrestador();

    String getNmOrgaoDestino();

    String getIndicadorOrgaoDestino();

    LocalDate getDtGeracao();

    String getVersao();

    String getVersaoBdsia();
}
