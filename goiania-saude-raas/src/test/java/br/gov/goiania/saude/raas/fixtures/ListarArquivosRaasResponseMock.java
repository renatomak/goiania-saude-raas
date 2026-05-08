package br.gov.goiania.saude.raas.fixtures;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public final class ListarArquivosRaasResponseMock {

    private ListarArquivosRaasResponseMock() { }

    public static ListarArquivosRaasResponse valido() {
        return new ListarArquivosRaasResponse(
                5,
                2026,
                LocalDate.of(2026, 5, 7),
                "123",
                "Empresa Teste",
                "/caminho/arquivo.txt",
                "3",
                new BigDecimal("100.00"));
    }

    public static List<ListarArquivosRaasResponse> listaPopular() {
        return List.of(valido(), valido(), valido());
    }
}
