package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.ArquivosRaasEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class ArquivosRaasEntityMock {

    private ArquivosRaasEntityMock() { }

    public static ArquivosRaasEntity valido() {
        return new ArquivosRaasEntity(
                1L,
                5,
                2026,
                LocalDate.of(2026, 5, 7),
                "123",
                "Empresa Teste",
                "/caminho/arquivo.txt",
                "3",
                new BigDecimal("100.00"),
                "textoExemplo"
        );
    }

    public static ArquivosRaasEntity exemplo() {
        return new ArquivosRaasEntity(
                2L,
                6,
                2027,
                LocalDate.of(2027, 6, 8),
                "456",
                "Empresa Exemplo",
                "/outro/caminho/arquivo.txt",
                "4",
                new BigDecimal("200.00"),
                "textoExemplo"
        );
    }
}

