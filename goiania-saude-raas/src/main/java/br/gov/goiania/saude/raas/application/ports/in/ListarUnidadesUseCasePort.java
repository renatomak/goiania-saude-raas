package br.gov.goiania.saude.raas.application.ports.in;

import br.gov.goiania.saude.raas.application.dto.ListarUnidadesResponse;

import java.util.List;

public interface ListarUnidadesUseCasePort {
    List<ListarUnidadesResponse> execute();
}

