package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.DownloadArquivosRaasPortIn;
import br.gov.goiania.saude.raas.application.ports.out.DownloadArquivosRaasPort;
import br.gov.goiania.saude.raas.infrastructure.mapper.DownloadArquivosRaasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadArquivosRaasUseCase implements DownloadArquivosRaasPortIn {

    private final DownloadArquivosRaasPort adapter;
    private final DownloadArquivosRaasMapper mapper;

    @Override
    public DownloadArquivosRaasResponse execute(final Long id) {
        return mapper.toResponse(adapter.execute(id));
    }
}
