package br.gov.goiania.saude.raas.domain.model;

import lombok.Getter;

@Getter
public class ListarUnidades {
    private final Long id;
    private final String nome;

    public ListarUnidades(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
