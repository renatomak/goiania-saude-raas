package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.ListarArquivosRaasEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class ListarArquivosRaasEntityMock {

    private ListarArquivosRaasEntityMock() { }

    public static ListarArquivosRaasEntity valido() {
        return new ListarArquivosRaasEntity(
                1L,
                5,
                2026,
                LocalDate.of(2026, 5, 7),
                "123",
                "Empresa Teste",
                "/caminho/arquivo.txt",
                "3",
                new BigDecimal("100.00")
        );
    }

    public static ListarArquivosRaasEntity exemplo() {
        return new ListarArquivosRaasEntity(
                2L,
                6,
                2027,
                LocalDate.of(2027, 6, 8),
                "456",
                "Empresa Exemplo",
                "/outro/caminho/arquivo.txt",
                "4",
                new BigDecimal("200.00")
        );
    }
}

