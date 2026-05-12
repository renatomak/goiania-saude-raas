package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.ListarUnidadesResponse;
import br.gov.goiania.saude.raas.application.ports.in.ListarUnidadesUseCasePort;
import br.gov.goiania.saude.raas.application.ports.out.ListarUnidadesPort;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarUnidadesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarUnidadesUseCase implements ListarUnidadesUseCasePort {

    private final ListarUnidadesPort adapter;
    private final ListarUnidadesMapper mapper;

    @Override
    public List<ListarUnidadesResponse> execute() {
        return adapter.execute()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}

