package br.gov.goiania.saude.raas.infrastructure.adapter.web.swagger;

import br.gov.goiania.saude.raas.application.dto.ListarUnidadesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Unidades", description = "Endpoints para listar unidades/empresas")
public interface ListarUnidadesSwagger {

    @GetMapping
    @Operation(
            summary = "Listar todas as unidades",
            description = "Retorna lista de todas as unidades/empresas disponíveis no sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de unidades recuperada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ListarUnidadesResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<List<ListarUnidadesResponse>> listarTodasAsUnidades();
}

