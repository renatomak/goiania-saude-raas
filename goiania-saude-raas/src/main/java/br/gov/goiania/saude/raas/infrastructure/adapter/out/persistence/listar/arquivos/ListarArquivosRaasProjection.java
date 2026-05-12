package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.arquivos;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ListarArquivosRaasProjection {

    Long getId();

    Integer getMes();

    Integer getAno();

    LocalDate getDtGeracao();

    Long getEmpresa();

    String getNomeEmpresa();

    String getPath();

    Integer getStatus();

    BigDecimal getTotalFolha();
}

