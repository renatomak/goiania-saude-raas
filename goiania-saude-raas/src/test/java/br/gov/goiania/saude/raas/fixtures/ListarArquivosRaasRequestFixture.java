package br.gov.goiania.saude.raas.fixtures;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;

public final class ListarArquivosRaasRequestFixture {

    private ListarArquivosRaasRequestFixture() { }

    public static ListarArquivosRaasRequest valido() {
        return new ListarArquivosRaasRequest(5, 2026, "123", 3);
    }

    public static ListarArquivosRaasRequest notFound() {
        return new ListarArquivosRaasRequest(5, 2026, "999", 3);
    }
}
