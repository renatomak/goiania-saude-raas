package br.gov.goiania.saude.raas.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ListarArquivosRaasResponse(
        Long id,
        @Schema(description = "Mês de referência do arquivo RAAS") Integer mes,
        @Schema(description = "Ano de referência do arquivo RAAS") Integer ano,
        @Schema(description = "Data de geração do arquivo RAAS", example = "31/10/2025")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataGeracao,
        @Schema(description = "Código da empresa") String codigoEmpresa,
        @Schema(description = "Nome da empresa") String nomeEmpresa,
        @Schema(description = "Caminho do arquivo RAAS") String path,
        @Schema(description = "Status do arquivo RAAS (3=GERADO, 6=CANCELADO)") String status,
        @Schema(description = "Valor total da folha do arquivo RAAS") BigDecimal totalFolha
) {
}
