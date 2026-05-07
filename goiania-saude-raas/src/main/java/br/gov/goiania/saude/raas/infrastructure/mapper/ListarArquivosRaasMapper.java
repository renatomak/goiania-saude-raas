package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.ListarArquivosRaasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ListarArquivosRaasMapper {

    @Mapping(target = "codigoEmpresa", source = "codigoEmpresa")
    @Mapping(target = "nomeEmpresa", source = "nomeEmpresa")
    @Mapping(target = "dataGeracao", source = "dataGeracao")
    ListarArquivosRaas toDomain(ListarArquivosRaasEntity entity);

    @Mapping(target = "codigoEmpresa", source = "codigoEmpresa")
    @Mapping(target = "nomeEmpresa", source = "nomeEmpresa")
    @Mapping(target = "dataGeracao", source = "dataGeracao")
    ListarArquivosRaasResponse toResponse(ListarArquivosRaas domain);
}
