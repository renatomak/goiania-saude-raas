package br.gov.goiania.saude.raas.infrastructure.adapter.in.web.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "RAAS Psicossocial", description = "Operações de geração de arquivo RAAS Atenção Psicossocial")
public interface GerarRaasPsicossocialSwagger {

    @Operation(
        summary = "Gerar arquivo RAAS Psicossocial",
        description = "Gera o arquivo texto RAAS Atenção Psicossocial (linhas 01, 15 e 16).",
        responses = {
            @ApiResponse(responseCode = "200", description = "Arquivo gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    ResponseEntity<String> gerar(
        @Parameter(description = "Mês (1-12)") Integer mes,
        @Parameter(description = "Ano (ex: 2025)") Integer ano
    );
}
