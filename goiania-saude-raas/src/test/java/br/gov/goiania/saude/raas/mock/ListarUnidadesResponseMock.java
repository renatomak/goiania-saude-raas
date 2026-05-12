package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.application.dto.ListarUnidadesResponse;

import java.util.List;

public final class ListarUnidadesResponseMock {

    private ListarUnidadesResponseMock() { }


    public static ListarUnidadesResponse valido() {
        return new ListarUnidadesResponse(
                123L,
                "Secretaria Municipal de Saúde"
        );
    }

    public static List<ListarUnidadesResponse> listaPopular() {
        return List.of(
                valido(),
                new ListarUnidadesResponse(456L, "Unidade Básica de Saúde Centro"),
                new ListarUnidadesResponse(789L, "Hospital Municipal")
        );
    }
}

