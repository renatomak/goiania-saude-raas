package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;

public final class ListarArquivosRaasFiltroMock {

    private ListarArquivosRaasFiltroMock() { }

    public static ListarArquivosRaasFiltro valido() {
        return new ListarArquivosRaasFiltro(
                5, 2026, "123", 3
        );
    }
}
