package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Table(name = "raas_psi")
@Getter
public class RaasPsiEntity {

    @Id
    @Column(name = "cd_raas_psi")
    private Long id;

    @Column(name = "cd_raas")
    private Long cdRaas;

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

    @Column(name = "dt_final_validade")
    private LocalDate dtFinalValidade;

    @Column(name = "nm_paciente")
    private String nmPaciente;

    @Column(name = "numero_prontuario")
    private Integer numeroProntuario;

    @Column(name = "nm_mae")
    private String nmMae;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero_logradouro")
    private String numeroLogradouro;

    @Column(name = "complemento_logradouro")
    private String complementoLogradouro;

    @Column(name = "cep")
    private String cep;

    @Column(name = "municipio")
    private Integer municipio;

    @Column(name = "dt_nascimento")
    private LocalDate dtNascimento;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "raca")
    private Integer raca;

    @Column(name = "nm_responsavel")
    private String nmResponsavel;

    @Column(name = "nacionalidade")
    private Integer nacionalidade;

    @Column(name = "etnia")
    private Integer etnia;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "celular")
    private String celular;

    @Column(name = "motivo_saida_permanencia")
    private Integer motivoSaidaPermanencia;

    @Column(name = "dt_ocorrencia")
    private LocalDate dtOcorrencia;

    @Column(name = "cid_principal")
    private String cidPrincipal;

    @Column(name = "cid_secundario_1")
    private String cidSecundario1;

    @Column(name = "cid_secundario_2")
    private String cidSecundario2;

    @Column(name = "cid_secundario_3")
    private String cidSecundario3;

    @Column(name = "cid_causas_associadas")
    private String cidCausasAssociadas;

    @Column(name = "carater_atendimento")
    private Integer caraterAtendimento;

    @Column(name = "origem_paciente")
    private Integer origemPaciente;

    @Column(name = "cobertura_esf")
    private String coberturaEsf;

    @Column(name = "codigo_cobertura_esf")
    private Integer codigoCoberturaEsf;

    @Column(name = "total_procedimentos")
    private Integer totalProcedimentos;

    @Column(name = "destino_paciente")
    private Integer destinoPaciente;

    @Column(name = "origem_informacoes")
    private String origemInformacoes;

    @Column(name = "situacao_rua")
    private String situacaoRua;

    @Column(name = "usuario_drogas")
    private String usuarioDrogas;

    @Column(name = "tipo_droga_alcool")
    private String tipoDrogaAlcool;

    @Column(name = "tipo_droga_crack")
    private String tipoDrogaCrack;

    @Column(name = "tipo_droga_outros")
    private String tipoDrogaOutros;

    @Column(name = "numero_autorizacao")
    private Long numeroAutorizacao;

    @Column(name = "descricao_bairro")
    private String descricaoBairro;

    @Column(name = "tipo_logradouro")
    private Integer tipoLogradouro;

    @Column(name = "email_paciente")
    private String emailPaciente;

    @Column(name = "cpf_paciente")
    private String cpfPaciente;
}
