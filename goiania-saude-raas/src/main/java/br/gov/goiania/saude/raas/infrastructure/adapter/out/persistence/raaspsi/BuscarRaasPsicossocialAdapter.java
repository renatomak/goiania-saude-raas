package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.Header;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import br.gov.goiania.saude.raas.application.ports.out.BuscarRaasPsicossocialPort;
import br.gov.goiania.saude.raas.application.service.RaasFormatacaoUtil;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuscarRaasPsicossocialAdapter implements BuscarRaasPsicossocialPort {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final String NOME_RESPONSAVEL = "FATURAMENTO SMS";
    private static final String SIGLA_RESPONSAVEL = "GCPAH";
    private static final String CNPJ_RESPONSAVEL = "25141524000123";
    private static final String NOME_DESTINO = "SECRETARIA MUN DE SAUDE DE GOIANIA";

    private final RaasPsiRepository repository;

    @Override
    public RaasRemessaPsicossocialDTO execute(final String competencia) {
        final String competenciaDate = competencia.substring(0, 4)
                + "-" + competencia.substring(4) + "-01";

        final RaasHeaderProjection headerProj =
                repository.buscarHeaderPorCompetencia(competenciaDate);
        final List<RaasPsiPacienteProjection> pacientesProj =
                repository.buscarPacientesPorCompetencia(competenciaDate);
        final List<RaasPsiItemProjection> itensProj =
                repository.buscarItensPorCompetencia(competenciaDate);

        final Map<Long, List<RaasPsiItemProjection>> itensPorPaciente =
                itensProj.stream()
                        .collect(Collectors.groupingBy(
                                RaasPsiItemProjection::getCdRaasPsi));

        final List<PacientePsicossocialDTO> pacientes = new ArrayList<>();
        for (final RaasPsiPacienteProjection p : pacientesProj) {
            pacientes.add(mapearPaciente(p, itensPorPaciente.getOrDefault(
                    p.getCdRaasPsi(), List.of())));
        }

        final RaasRemessaPsicossocialDTO remessa = new RaasRemessaPsicossocialDTO();
        remessa.setHeader(criarHeader(competencia, headerProj));
        remessa.setPacientes(pacientes);
        return remessa;
    }

    private Header criarHeader(final String competencia,
                               final RaasHeaderProjection headerProj) {
        final Header header = new Header();
        header.setCompetencia(competencia);
        header.setNomeResponsavel(NOME_RESPONSAVEL);
        header.setSiglaResponsavel(SIGLA_RESPONSAVEL);
        header.setCnpjResponsavel(CNPJ_RESPONSAVEL);
        header.setNomeDestino(NOME_DESTINO);
        if (headerProj != null) {
            header.setCampoControle(headerProj.getCampoControle());
            header.setDataGeracao(headerProj.getDtGeracao() != null
                    ? headerProj.getDtGeracao().format(FORMATO_DATA) : "");
            header.setVersaoSistema(headerProj.getVersao());
            header.setVersaoBdsia(competencia + "a");
        }
        return header;
    }

    private PacientePsicossocialDTO mapearPaciente(
            final RaasPsiPacienteProjection p,
            final List<RaasPsiItemProjection> itens) {
        final PacientePsicossocialDTO dto = mapearDadosPessoais(p);
        dto.setNumeroProntuario(p.getNumeroProntuario() != null
                ? p.getNumeroProntuario().toString() : "");
        dto.setOrigemPaciente(RaasFormatacaoUtil.formatarOrigem(p.getOrigemPaciente()));
        dto.setSituacaoRua(RaasFormatacaoUtil.defaultString(p.getSituacaoRua(), "N"));
        dto.setUsuarioDrogas(RaasFormatacaoUtil.defaultString(p.getUsuarioDrogas(), "N"));
        dto.setTipoDrogaAlcool(RaasFormatacaoUtil.defaultString(
                p.getTipoDrogaAlcool(), " "));
        dto.setTipoDrogaCrack(RaasFormatacaoUtil.defaultString(
                p.getTipoDrogaCrack(), " "));
        dto.setTipoDrogaOutros(RaasFormatacaoUtil.defaultString(
                p.getTipoDrogaOutros(), " "));
        dto.setNumeroAutorizacao(p.getNumeroAutorizacao() != null
                ? p.getNumeroAutorizacao().toString() : "");
        dto.setDescricaoBairro(p.getDescricaoBairro());
        dto.setTipoLogradouro(RaasFormatacaoUtil.formatarTipoLogradouro(
                p.getTipoLogradouro()));
        dto.setEmailPaciente(p.getEmailPaciente());
        dto.setAcoes(mapearAcoes(itens));
        return dto;
    }

    private PacientePsicossocialDTO mapearDadosPessoais(
            final RaasPsiPacienteProjection p) {
        final PacientePsicossocialDTO dto = mapearDadosBasicos(p);
        mapearCidsEAdministrativo(p, dto);
        return dto;
    }

    private PacientePsicossocialDTO mapearDadosBasicos(
            final RaasPsiPacienteProjection p) {
        final PacientePsicossocialDTO dto = new PacientePsicossocialDTO();
        mapearIdentificacao(p, dto);
        mapearEndereco(p, dto);
        mapearContato(p, dto);
        return dto;
    }

    private void mapearIdentificacao(final RaasPsiPacienteProjection p,
                                      final PacientePsicossocialDTO dto) {
        dto.setUf(RaasFormatacaoUtil.formatarUf(p.getUnidadeFederacao()));
        dto.setCompetencia(RaasFormatacaoUtil.formatarCompetencia(p.getCompetencia()));
        dto.setCnes(RaasFormatacaoUtil.formatarCnes(p.getUnidadePrestadoraServico()));
        dto.setCnsPaciente(RaasFormatacaoUtil.formatarCns(p.getCartaoNacionalSaude()));
        dto.setCpfPaciente(RaasFormatacaoUtil.formatarCpf(p.getCpfPaciente()));
        dto.setDataInicio(RaasFormatacaoUtil.formatarData(p.getDtInicioValidade()));
        dto.setDataFim(RaasFormatacaoUtil.formatarData(p.getDtFinalValidade()));
        dto.setNomePaciente(p.getNmPaciente());
        dto.setNomeMae(p.getNmMae());
    }

    private void mapearEndereco(final RaasPsiPacienteProjection p,
                                 final PacientePsicossocialDTO dto) {
        dto.setLogradouro(p.getLogradouro());
        dto.setNumeroEndereco(p.getNumeroLogradouro());
        dto.setComplemento(p.getComplementoLogradouro());
        dto.setCep(p.getCep());
        dto.setMunicipioIbge(RaasFormatacaoUtil.formatarMunicipio(p.getMunicipio()));
        dto.setDataNascimento(RaasFormatacaoUtil.formatarData(p.getDtNascimento()));
        dto.setSexo(p.getSexo());
        dto.setRacaCor(RaasFormatacaoUtil.formatarRaca(p.getRaca()));
        dto.setNomeResponsavel(p.getNmResponsavel());
    }

    private void mapearContato(final RaasPsiPacienteProjection p,
                                final PacientePsicossocialDTO dto) {
        dto.setEtnia(RaasFormatacaoUtil.formatarEtnia(p.getEtnia()));
        dto.setCelular(p.getCelular());
        dto.setTelefone(p.getTelefone());
        dto.setMotivoSaida(RaasFormatacaoUtil.formatarMotivoSaida(
                p.getMotivoSaidaPermanencia()));
        dto.setCidPrincipal(p.getCidPrincipal());
        dto.setCoberturaEsf(RaasFormatacaoUtil.defaultString(
                p.getCoberturaEsf(), "N"));
        dto.setCnesEsf(RaasFormatacaoUtil.formatarCnes(
                p.getCodigoCoberturaEsf()));
        dto.setDestinoPaciente(RaasFormatacaoUtil.formatarDestino(
                p.getDestinoPaciente()));
    }

    private void mapearCidsEAdministrativo(final RaasPsiPacienteProjection p,
                                            final PacientePsicossocialDTO dto) {
        dto.setNacionalidade(RaasFormatacaoUtil.formatarNacionalidade(p.getNacionalidade()));
        dto.setDtOcorrencia(RaasFormatacaoUtil.formatarData(p.getDtOcorrencia()));
        dto.setCidSecundario1(p.getCidSecundario1());
        dto.setCidSecundario2(p.getCidSecundario2());
        dto.setCidSecundario3(p.getCidSecundario3());
        dto.setCidCausasAssociadas(p.getCidCausasAssociadas());
        dto.setCaraterAtendimento(RaasFormatacaoUtil.formatarCarater(p.getCaraterAtendimento()));
        dto.setOrigemInformacoes(RaasFormatacaoUtil.defaultString(
                p.getOrigemInformacoes(), "RAS"));
    }

    private List<AcaoPsicossocialDTO> mapearAcoes(
            final List<RaasPsiItemProjection> itens) {
        final List<AcaoPsicossocialDTO> acoes = new ArrayList<>();
        for (final RaasPsiItemProjection i : itens) {
            final AcaoPsicossocialDTO dto = new AcaoPsicossocialDTO();
            dto.setProcedimento(RaasFormatacaoUtil.formatarProcedimento(i.getCodProcedimento()));
            dto.setCbo(i.getCodCboExecutante());
            dto.setCnsProfissional(i.getCnsExecutante());
            dto.setDataExecucao(RaasFormatacaoUtil.formatarData(i.getDtExecucaoProcedimento()));
            dto.setClassificacao(RaasFormatacaoUtil.formatarClassificacao(i.getClassificacao()));
            dto.setQuantidade(i.getQuantidadeRealizada() != null
                    ? i.getQuantidadeRealizada() : 1);
            dto.setServico(RaasFormatacaoUtil.formatarServico(i.getServico()));
            dto.setLocalRealizacao(RaasFormatacaoUtil.defaultString(
                    i.getLocalRealizacao(), "C"));
            dto.setOrigemInformacoes(RaasFormatacaoUtil.defaultString(
                    i.getOrigemInformacoes(), "RAS"));
            acoes.add(dto);
        }
        return acoes;
    }
}
