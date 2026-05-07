package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.ListarArquivosRaasUseCasePort;
import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;
import br.gov.goiania.saude.raas.domain.service.ListarArquivosRaasDomainService;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListarArquivosRaasUseCase implements ListarArquivosRaasUseCasePort {

    private final ListarArquivosRaasPort repositoryPort;
    private final ListarArquivosRaasDomainService domainService;
    private final ListarArquivosRaasMapper mapper;

    @Override
    public List<ListarArquivosRaasResponse> execute(final ListarArquivosRaasRequest request) {
        final ListarArquivosRaasFiltro filtro = new ListarArquivosRaasFiltro(
                request.mes(),
                request.ano(),
                request.codigoEmpresa(),
                request.situacao()
        );

        final List<ListarArquivosRaas> registros = repositoryPort.execute(filtro);
        final List<ListarArquivosRaas> validados = domainService.validarERetornarResultados(registros, filtro);

        return validados.stream()
                .map(mapper::toResponse)
                .toList();
    }
}
