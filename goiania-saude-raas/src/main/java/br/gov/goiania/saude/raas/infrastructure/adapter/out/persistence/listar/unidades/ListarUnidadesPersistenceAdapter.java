package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.unidades;

import br.gov.goiania.saude.raas.application.ports.out.ListarUnidadesPort;
import br.gov.goiania.saude.raas.domain.model.ListarUnidades;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarUnidadesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListarUnidadesPersistenceAdapter implements ListarUnidadesPort {

    private final ListarUnidadesRepository repository;
    private final ListarUnidadesMapper mapper;

    @Override
    public List<ListarUnidades> execute() {
        return repository.listarTodasAsUnidades()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}

