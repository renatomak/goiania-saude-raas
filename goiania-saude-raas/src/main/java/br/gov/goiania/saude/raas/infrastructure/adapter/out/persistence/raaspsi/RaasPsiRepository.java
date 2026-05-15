package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RaasPsiRepository extends JpaRepository<RaasPsiEntity, Long> {

    @Query(value = """
            SELECT
                p.cd_raas_psi AS cdRaasPsi,
                p.unidade_federacao AS unidadeFederacao,
                p.competencia AS competencia,
                p.unidade_prestadora_servico AS unidadePrestadoraServico,
                p.cartao_nacional_saude AS cartaoNacionalSaude,
                p.dt_inicio_validade AS dtInicioValidade,
                p.dt_final_validade AS dtFinalValidade,
                p.nm_paciente AS nmPaciente,
                p.numero_prontuario AS numeroProntuario,
                p.nm_mae AS nmMae,
                p.logradouro AS logradouro,
                p.numero_logradouro AS numeroLogradouro,
                p.complemento_logradouro AS complementoLogradouro,
                p.cep AS cep,
                p.municipio AS municipio,
                p.dt_nascimento AS dtNascimento,
                p.sexo AS sexo,
                p.raca AS raca,
                p.nm_responsavel AS nmResponsavel,
                p.nacionalidade AS nacionalidade,
                p.etnia AS etnia,
                p.telefone AS telefone,
                p.celular AS celular,
                p.motivo_saida_permanencia AS motivoSaidaPermanencia,
                p.dt_ocorrencia AS dtOcorrencia,
                p.cid_principal AS cidPrincipal,
                p.cid_secundario_1 AS cidSecundario1,
                p.cid_secundario_2 AS cidSecundario2,
                p.cid_secundario_3 AS cidSecundario3,
                p.cid_causas_associadas AS cidCausasAssociadas,
                p.carater_atendimento AS caraterAtendimento,
                p.origem_paciente AS origemPaciente,
                p.cobertura_esf AS coberturaEsf,
                p.codigo_cobertura_esf AS codigoCoberturaEsf,
                p.total_procedimentos AS totalProcedimentos,
                p.destino_paciente AS destinoPaciente,
                p.origem_informacoes AS origemInformacoes,
                p.situacao_rua AS situacaoRua,
                p.usuario_drogas AS usuarioDrogas,
                p.tipo_droga_alcool AS tipoDrogaAlcool,
                p.tipo_droga_crack AS tipoDrogaCrack,
                p.tipo_droga_outros AS tipoDrogaOutros,
                p.numero_autorizacao AS numeroAutorizacao,
                p.descricao_bairro AS descricaoBairro,
                p.tipo_logradouro AS tipoLogradouro,
                p.email_paciente AS emailPaciente,
                p.cpf_paciente AS cpfPaciente
            FROM raas_psi p
            WHERE p.competencia = CAST(:competencia AS date)
            ORDER BY p.cd_raas_psi
            """, nativeQuery = true)
    List<RaasPsiPacienteProjection> buscarPacientesPorCompetencia(
            @Param("competencia") String competencia
    );

    @Query(value = """
            SELECT
                i.cd_raas_psi AS cdRaasPsi,
                i.unidade_federacao AS unidadeFederacao,
                i.competencia AS competencia,
                i.unidade_prestadora_servico AS unidadePrestadoraServico,
                i.cartao_nacional_saude AS cartaoNacionalSaude,
                i.dt_inicio_validade AS dtInicioValidade,
                i.cod_procedimento AS codProcedimento,
                i.cod_cbo_executante AS codCboExecutante,
                i.cns_executante AS cnsExecutante,
                i.dt_execucao_procedimento AS dtExecucaoProcedimento,
                i.servico AS servico,
                i.classificacao AS classificacao,
                i.quantidade_realizada AS quantidadeRealizada,
                i.origem_informacoes AS origemInformacoes,
                i.local_realizacao AS localRealizacao,
                i.cpf_paciente AS cpfPaciente
            FROM raas_psi_item i
            INNER JOIN raas_psi p ON p.cd_raas_psi = i.cd_raas_psi
            WHERE p.competencia = CAST(:competencia AS date)
            ORDER BY i.cd_raas_psi, i.cd_raas_psi_item
            """, nativeQuery = true)
    List<RaasPsiItemProjection> buscarItensPorCompetencia(
            @Param("competencia") String competencia
    );
    @Query(value = """
            SELECT
                r.quantidade_folhas AS quantidadeFolhas,
                r.campo_controle AS campoControle,
                r.nm_orgao_origem AS nmOrgaoOrigem,
                r.sigla_orgao_origem AS siglaOrgaoOrigem,
                r.cgc_prestador AS cgcPrestador,
                r.nm_orgao_destino AS nmOrgaoDestino,
                r.indicador_orgao_destino AS indicadorOrgaoDestino,
                r.dt_geracao AS dtGeracao,
                r.versao AS versao,
                r.versao_bdsia AS versaoBdsia
            FROM raas r
            WHERE r.competencia = CAST(:competencia AS date)
            LIMIT 1
            """, nativeQuery = true)
    RaasHeaderProjection buscarHeaderPorCompetencia(
            @Param("competencia") String competencia
    );
}
