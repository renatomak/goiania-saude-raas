package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.application.dto.HeaderDTO;

public final class HeaderDTOMock {

    private HeaderDTOMock() { }

    public static HeaderDTO valido() {
        HeaderDTO dto = new HeaderDTO();
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

    public static HeaderDTO exemplo() {
        HeaderDTO dto = new HeaderDTO();
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
}

