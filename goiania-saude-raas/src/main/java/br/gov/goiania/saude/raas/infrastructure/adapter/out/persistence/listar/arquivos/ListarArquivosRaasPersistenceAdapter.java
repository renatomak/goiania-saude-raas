package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.arquivos;

import br.gov.goiania.saude.raas.application.ports.out.ListarArquivosRaasPort;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaasFiltro;
import br.gov.goiania.saude.raas.infrastructure.mapper.ListarArquivosRaasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListarArquivosRaasPersistenceAdapter implements ListarArquivosRaasPort {

    private final ListarArquivosRaasRepository repository;
    private final ListarArquivosRaasMapper mapper;

    @Override
    public List<ListarArquivosRaas> execute(final ListarArquivosRaasFiltro filtro) {
        return repository
                .buscarProcessosPorCompetenciaEmpresaEStatus(
                        filtro.mes(),
                        filtro.ano(),
                        filtro.codigoEmpresa(),
                        filtro.status()
                )
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
