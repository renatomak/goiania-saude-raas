package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;
import br.gov.goiania.saude.raas.domain.model.DownloadArquivosRaas;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload.DownloadArquivosRaasProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DownloadArquivosRaasMapper {

    @Mapping(source = "dtGeracao", target = "dataGeracao")
    DownloadArquivosRaas toDomain(DownloadArquivosRaasProjection projection);

    DownloadArquivosRaasResponse toResponse(DownloadArquivosRaas domain);
}
