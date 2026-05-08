package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload;

import br.gov.goiania.saude.raas.application.ports.out.DownloadArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.model.DownloadArquivosRaas;
import br.gov.goiania.saude.raas.infrastructure.mapper.DownloadArquivosRaasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DownloadArquivosRaasPersistenceAdapter implements DownloadArquivosRaasPort {

    private final DownloadArquivosRaasRepository repository;
    private final DownloadArquivosRaasMapper mapper;

    @Override
    public DownloadArquivosRaas execute(final Long id) {
        return mapper.toDomain(repository.download(id));
    }
}
