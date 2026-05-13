package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;
import br.gov.goiania.saude.raas.domain.model.DownloadArquivosRaas;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.dowload.DownloadArquivosRaasProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface DownloadArquivosRaasMapper {

    DownloadArquivosRaas toDomain(DownloadArquivosRaasProjection projection);

    @Mapping(source = "nome", target = "nome", qualifiedByName = "limparNome")
    DownloadArquivosRaasResponse toResponse(DownloadArquivosRaas domain);

    @Named("limparNome")
    default String limparNome(String nome) {
        if (nome == null) {
            return null;
        }
        return nome.replace("raas/", "");
    }

}
