package br.gov.goiania.saude.raas.application.ports.out;

import br.gov.goiania.saude.raas.domain.model.ListarUnidades;

import java.util.List;

public interface ListarUnidadesPort {
    List<ListarUnidades> execute();
}

