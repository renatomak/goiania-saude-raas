package br.gov.goiania.saude.raas.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ListarUnidadesResponse(
        @Schema(description = "Identificador da unidade") Long id,
        @Schema(description = "Nome da unidade") String nome
) {
}

