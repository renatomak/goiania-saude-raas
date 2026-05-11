package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.DownloadArquivosRaasUseCasePort;
import br.gov.goiania.saude.raas.application.ports.out.DownloadArquivosRaasPort;
import br.gov.goiania.saude.raas.infrastructure.mapper.DownloadArquivosRaasMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DownloadArquivosRaasUseCase implements DownloadArquivosRaasUseCasePort {

    private final DownloadArquivosRaasPort adapter;
    private final DownloadArquivosRaasMapper mapper;

    @Override
    public DownloadArquivosRaasResponse execute(final Long id) {
        return mapper.toResponse(adapter.execute(id));
    }
}
