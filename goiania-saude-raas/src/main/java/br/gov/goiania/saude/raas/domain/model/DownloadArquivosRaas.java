package br.gov.goiania.saude.raas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class DownloadArquivosRaas {

    private final Long id;
    private final LocalDate dataGeracao;
    private final String path;
    private final String texto;
}
