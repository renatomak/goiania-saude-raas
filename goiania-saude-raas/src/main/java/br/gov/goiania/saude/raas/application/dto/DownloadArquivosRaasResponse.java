package br.gov.goiania.saude.raas.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record DownloadArquivosRaasResponse(
        @Schema(description = "ID do arquivo RAAS") Long id,
        @Schema(description = "Data de geração do arquivo RAAS", example = "07/05/2026")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataGeracao,
        @Schema(description = "Caminho do arquivo RAAS") String path,
        @Schema(description = "Texto do arquivo RAAS") String texto
) {
}
