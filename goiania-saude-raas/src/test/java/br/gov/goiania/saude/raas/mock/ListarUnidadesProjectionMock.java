package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.unidades.ListarUnidadesProjection;

public final class ListarUnidadesProjectionMock {

    private ListarUnidadesProjectionMock() { }

    public static ListarUnidadesProjection valido() {
        return new ListarUnidadesProjection() {
            @Override
            public String getId() {
                return "123";
            }

            @Override
            public String getNome() {
                return "Secretaria Municipal de Saúde";
            }
        };
    }
}

