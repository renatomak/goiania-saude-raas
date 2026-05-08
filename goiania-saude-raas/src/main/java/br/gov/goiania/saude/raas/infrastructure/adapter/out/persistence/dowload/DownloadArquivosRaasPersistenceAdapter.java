package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload;

import br.gov.goiania.saude.raas.application.ports.out.DownloadArquivosRaasPort;
import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.model.DownloadArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.ListarArquivosRaasRepository;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DownloadArquivosRaasPersistenceAdapter implements DownloadArquivosRaasPort {

    private final DownloadArquivosRaasRepository repository;
    private final ListarArquivosRaasMapper mapper;

    @Override
    public DownloadArquivosRaas execute(final Long id) {
        return repository
                .download(id)
                .map(mapper::toDomain);
    }
}
