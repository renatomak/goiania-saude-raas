package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;
import br.gov.goiania.saude.raas.domain.model.ListarArquivosRaas;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.ListarArquivosRaasEntity;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.ListarArquivosRaasProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ListarArquivosRaasMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "codigoEmpresa", source = "codigoEmpresa")
    @Mapping(target = "nomeEmpresa", source = "nomeEmpresa")
    @Mapping(target = "dataGeracao", source = "dataGeracao")
    ListarArquivosRaas toDomain(ListarArquivosRaasEntity entity);

    @Mapping(target = "dataGeracao", source = "dtGeracao")
    @Mapping(target = "codigoEmpresa", source = "empresa")
    ListarArquivosRaas toDomain(ListarArquivosRaasProjection projection);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "codigoEmpresa", source = "codigoEmpresa")
    @Mapping(target = "nomeEmpresa", source = "nomeEmpresa")
    @Mapping(target = "dataGeracao", source = "dataGeracao")
    ListarArquivosRaasResponse toResponse(ListarArquivosRaas domain);
}
