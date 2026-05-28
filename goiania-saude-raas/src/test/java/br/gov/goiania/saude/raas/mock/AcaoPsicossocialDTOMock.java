package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;

public final class AcaoPsicossocialDTOMock {

    private AcaoPsicossocialDTOMock() { }

    public static AcaoPsicossocialDTO valido() {
        AcaoPsicossocialDTO dto = new AcaoPsicossocialDTO();
        dto.setProcedimento("1234567890");
        dto.setCbo("123456");
        dto.setCnsProfissional("123456789012345");
        dto.setDataExecucao("20240518");
        dto.setClassificacao("001");
        dto.setQuantidade(5);
        dto.setServico("001");
        dto.setLocalRealizacao("A");
        dto.setOrigemInformacoes("EXT");
        return dto;
    }

    public static AcaoPsicossocialDTO exemplo() {
        AcaoPsicossocialDTO dto = new AcaoPsicossocialDTO();
        dto.setProcedimento("0987654321");
        dto.setCbo("654321");
        dto.setCnsProfissional("543210987654321");
        dto.setDataExecucao("20240619");
        dto.setClassificacao("002");
        dto.setQuantidade(10);
        dto.setServico("002");
        dto.setLocalRealizacao("B");
        dto.setOrigemInformacoes("RAS");
        return dto;
    }
}

