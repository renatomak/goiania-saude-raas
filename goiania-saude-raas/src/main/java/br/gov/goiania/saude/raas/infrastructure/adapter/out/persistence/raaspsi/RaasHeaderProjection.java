package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import java.time.LocalDate;

public interface RaasHeaderProjection {

    Long getQuantidadeFolhas();

    Long getCampoControle();

    LocalDate getDtGeracao();

    String getVersao();
}
