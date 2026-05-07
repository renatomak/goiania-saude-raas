package br.gov.goiania.saude.raas.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ListarArquivosRaasRequest(
        @NotNull(message = "Mes e obrigatorio")
        @Min(value = 1, message = "Mes deve ser entre 1 e 12")
        @Max(value = 12, message = "Mes deve ser entre 1 e 12")
        Integer mes,

        @NotNull(message = "Ano e obrigatorio")
        @Min(value = 2000, message = "Ano invalido")
        Integer ano,

        @NotBlank(message = "Codigo da empresa e obrigatorio")
        String codigoEmpresa,

        @NotEmpty(message = "Ao menos um status deve ser informado")
        List<String> situacao
) {
}
