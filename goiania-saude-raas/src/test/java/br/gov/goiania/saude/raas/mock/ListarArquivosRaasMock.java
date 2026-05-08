package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public final class ListarArquivosRaasMock {

    private ListarArquivosRaasMock() { }

    public static ListarArquivosRaas valido() {
        return new ListarArquivosRaas(
                1L,
                5,
                2026,
                LocalDate.of(2026, 5, 7),
                "123",
                "Empresa Teste",
                "/caminho/arquivo.txt",
                "3",
                new BigDecimal("100.00"));
    }

    public static List<ListarArquivosRaas> listaPopular() {
        return List.of(valido(), valido(), valido());
    }
}
