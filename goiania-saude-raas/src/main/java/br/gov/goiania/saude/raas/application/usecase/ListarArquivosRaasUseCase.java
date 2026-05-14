package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.ListarArquivosRaasUseCasePort;
import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


@RequiredArgsConstructor
public class ListarArquivosRaasUseCase implements ListarArquivosRaasUseCasePort {

    private final ListarArquivosRaasPort repositoryPort;
    private final ListarArquivosRaasMapper mapper;

    @Override
    public Page<ListarArquivosRaasResponse> execute(final ListarArquivosRaasRequest request) {
        final ListarArquivosRaasFiltro filtro = new ListarArquivosRaasFiltro(
                request.mes(),
                request.ano(),
                request.codigoEmpresa(),
                request.situacao()
        );
        int page = request.page() != null ? request.page() : 0;
        int size = request.size() != null ? request.size() : 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ListarArquivosRaas> pageResult = repositoryPort.execute(filtro, pageRequest);
        if (pageResult.isEmpty()) {
            throw new br.gov.goiania.saude.raas.domain.exception.ListarArquivosRaasNotFoundException(
                request.codigoEmpresa(), request.mes(), request.ano()
            );
        }
        return pageResult.map(mapper::toResponse);
    }
}
