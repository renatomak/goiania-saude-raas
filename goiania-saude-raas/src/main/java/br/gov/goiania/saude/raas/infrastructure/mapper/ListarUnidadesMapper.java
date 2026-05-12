package br.gov.goiania.saude.raas.infrastructure.mapper;

import br.gov.goiania.saude.raas.application.dto.ListarUnidadesResponse;
import br.gov.goiania.saude.raas.domain.model.ListarUnidades;
import br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.listar.unidades.ListarUnidadesProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListarUnidadesMapper {

    ListarUnidades toDomain(ListarUnidadesProjection projection);

    ListarUnidadesResponse toResponse(ListarUnidades domain);
}
