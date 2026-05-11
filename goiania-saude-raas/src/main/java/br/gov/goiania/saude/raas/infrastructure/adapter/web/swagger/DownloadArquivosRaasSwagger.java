package br.gov.goiania.saude.raas.infrastructure.adapter.web.swagger;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "RAAS", description = "Operações de consulta de arquivos RAAS")
public interface DownloadArquivosRaasSwagger {

    @Operation(
        summary = "Download de arquivo RAAS",
        description = "Retorna os detalhes de um arquivo RAAS específico para download.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Download realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Arquivo RAAS não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    ResponseEntity<DownloadArquivosRaasResponse> download(
        @Parameter(description = "ID do arquivo RAAS") Long id
    );
}

