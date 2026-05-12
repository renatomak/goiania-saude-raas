package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.domain.model.ListarUnidades;

public final class ListarUnidadesMock {

    private ListarUnidadesMock() { }

    public static ListarUnidades valido() {
        return new ListarUnidades(123L, "Secretaria Municipal de Saúde");
    }
}

