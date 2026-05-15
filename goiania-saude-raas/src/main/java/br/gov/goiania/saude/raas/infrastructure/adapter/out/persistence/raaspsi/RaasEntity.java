package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Table(name = "raas")
@Getter
public class RaasEntity {

    @Id
    @Column(name = "cd_raas")
    private Long id;

    @Column(name = "linha")
    private Integer linha;

    @Column(name = "indicador_inicio")
    private String indicadorInicio;

    @Column(name = "competencia")
    private LocalDate competencia;

    @Column(name = "quantidade_folhas")
    private Long quantidadeFolhas;

    @Column(name = "campo_controle")
    private Long campoControle;

    @Column(name = "nm_orgao_origem")
    private String nmOrgaoOrigem;

    @Column(name = "sigla_orgao_origem")
    private String siglaOrgaoOrigem;

    @Column(name = "cgc_prestador")
    private Long cgcPrestador;

    @Column(name = "nm_orgao_destino")
    private String nmOrgaoDestino;

    @Column(name = "indicador_orgao_destino")
    private String indicadorOrgaoDestino;

    @Column(name = "dt_geracao")
    private LocalDate dtGeracao;

    @Column(name = "versao")
    private String versao;

    @Column(name = "versao_bdsia")
    private String versaoBdsia;

    @Column(name = "status")
    private Integer status;

    @Column(name = "empresa")
    private Long empresa;
}
