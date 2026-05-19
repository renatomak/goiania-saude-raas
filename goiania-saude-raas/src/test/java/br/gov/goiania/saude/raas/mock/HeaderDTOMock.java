package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.application.dto.Header;

public final class HeaderDTOMock {

    private HeaderDTOMock() { }

    public static Header valido() {
        Header dto = new Header();
        dto.setCompetencia("202405");
        dto.setQuantidadeFolhas(12L);
        dto.setCampoControle(1234L);
        dto.setNomeResponsavel("FATURAMENTO SMS GCPAH");
        dto.setSiglaResponsavel("251415");
        dto.setCnpjResponsavel("12345678000195");
        dto.setNomeDestino("DESTINO TESTE");
        dto.setDataGeracao("20240518");
        dto.setVersaoSistema("02.21");
        dto.setVersaoBdsia("202405a");
        return dto;
    }

    public static Header exemplo() {
        Header dto = new Header();
        dto.setCompetencia("202406");
        dto.setQuantidadeFolhas(34L);
        dto.setCampoControle(5678L);
        dto.setNomeResponsavel("NOME EXEMPLO");
        dto.setSiglaResponsavel("654321");
        dto.setCnpjResponsavel("98765432000123");
        dto.setNomeDestino("DESTINO EXEMPLO");
        dto.setDataGeracao("20240619");
        dto.setVersaoSistema("02.21");
        dto.setVersaoBdsia("202406a");
        return dto;
    }

    public static Header vazio() {
        Header dto = new Header();
        dto.setCompetencia("202405");
        dto.setQuantidadeFolhas(0L);
        dto.setDataGeracao("20240518");
        dto.setVersaoSistema("02.21");
        dto.setVersaoBdsia("202405a");
        return dto;
    }

    public static Header minimo() {
        Header dto = new Header();
        dto.setCompetencia("1");
        dto.setQuantidadeFolhas(1L);
        dto.setNomeResponsavel("A");
        dto.setSiglaResponsavel("B");
        dto.setCnpjResponsavel("1");
        dto.setNomeDestino("C");
        dto.setDataGeracao("20240518");
        dto.setVersaoSistema("1.0.0");
        dto.setVersaoBdsia("2.0.0");
        return dto;
    }

    public static Header nulo() {
        Header dto = new Header();
        dto.setCompetencia(null);
        dto.setQuantidadeFolhas(null);
        dto.setNomeResponsavel(null);
        dto.setSiglaResponsavel(null);
        dto.setCnpjResponsavel(null);
        dto.setNomeDestino(null);
        dto.setDataGeracao(null);
        dto.setVersaoSistema(null);
        dto.setVersaoBdsia(null);
        return dto;
    }
}

