package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Table(name = "raas_psi_item")
@Getter
public class RaasPsiItemEntity {

    @Id
    @Column(name = "cd_raas_psi_item")
    private Long id;

    @Column(name = "cd_raas_psi")
    private Long cdRaasPsi;

    @Column(name = "linha")
    private Integer linha;

    @Column(name = "unidade_federacao")
    private Integer unidadeFederacao;

    @Column(name = "competencia")
    private LocalDate competencia;

    @Column(name = "unidade_prestadora_servico")
    private Integer unidadePrestadoraServico;

    @Column(name = "cartao_nacional_saude")
    private String cartaoNacionalSaude;

    @Column(name = "dt_inicio_validade")
    private LocalDate dtInicioValidade;

    @Column(name = "cod_procedimento")
    private Long codProcedimento;

    @Column(name = "cod_cbo_executante")
    private String codCboExecutante;

    @Column(name = "cns_executante")
    private String cnsExecutante;

    @Column(name = "dt_execucao_procedimento")
    private LocalDate dtExecucaoProcedimento;

    @Column(name = "servico")
    private Integer servico;

    @Column(name = "classificacao")
    private Integer classificacao;

    @Column(name = "quantidade_realizada")
    private Integer quantidadeRealizada;

    @Column(name = "origem_informacoes")
    private String origemInformacoes;

    @Column(name = "local_realizacao")
    private String localRealizacao;

    @Column(name = "cpf_paciente")
    private String cpfPaciente;
}
