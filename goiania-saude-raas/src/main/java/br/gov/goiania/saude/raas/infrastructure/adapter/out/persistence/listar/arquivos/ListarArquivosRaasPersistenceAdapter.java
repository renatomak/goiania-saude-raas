package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.arquivos;

import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Component
@RequiredArgsConstructor
public class ListarArquivosRaasPersistenceAdapter implements ListarArquivosRaasPort {

    private final ListarArquivosRaasRepository repository;
    private final ListarArquivosRaasMapper mapper;

    @Override
    public Page<ListarArquivosRaas> execute(final ListarArquivosRaasFiltro filtro, Pageable pageable) {
        return repository
                .buscarProcessosPorCompetenciaEmpresaEStatus(
                        filtro.mes(),
                        filtro.ano(),
                        filtro.codigoEmpresa(),
                        filtro.status(),
                        pageable
                )
                .map(mapper::toDomain);
    }
}
