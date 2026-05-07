package br.gov.goiania.saude.raas.infrastructure.adapter.web.swagger;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "RAAS", description = "Operações de consulta de arquivos RAAS")
public interface ListarArquivosRaasSwagger {

    @Operation(
        summary = "Listar arquivos RAAS",
        description = "Retorna uma lista de arquivos RAAS filtrando por mês, ano, empresa e situação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    ResponseEntity<List<ListarArquivosRaasResponse>> listarRaas(
        @Parameter(description = "Mês de referência do arquivo") Integer mes,
        @Parameter(description = "Ano de referência do arquivo") Integer ano,
        @Parameter(description = "Código da empresa") String codigoEmpresa,
        @Parameter(description = "Situação do arquivo: 3 para GERADO, 6 para CANCELADO") Integer situacao
    );
}

