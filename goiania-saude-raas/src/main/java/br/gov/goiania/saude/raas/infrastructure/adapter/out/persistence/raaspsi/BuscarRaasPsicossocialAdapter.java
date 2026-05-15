package br.gov.goiania.saude.raas.infrastructure.adapter.out.persistence.raaspsi;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.HeaderDTO;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import br.gov.goiania.saude.raas.application.ports.out.BuscarRaasPsicossocialPort;
import java.time.LocalDate;
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

    private static final DateTimeFormatter FORMATO_COMPETENCIA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter FORMATO_COMPETENCIA_HEADER =
            DateTimeFormatter.ofPattern("yyyyMM");

    private final RaasPsiRepository repository;

    @Override
    public RaasRemessaPsicossocialDTO execute(final String competencia) {
        final String competenciaDate = competencia.substring(0, 4)
                + "-" + competencia.substring(4) + "-01";

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
        remessa.setHeader(criarHeader(competencia));
        remessa.setPacientes(pacientes);
        return remessa;
    }

    private HeaderDTO criarHeader(final String competencia) {
        final HeaderDTO header = new HeaderDTO();
        header.setCompetencia(competencia);
        header.setNomeResponsavel("SECRETARIA MUNICIPAL DE SAUDE");
        header.setSiglaResponsavel("SMSGO");
        header.setCnpjResponsavel("00000000000000");
        header.setNomeDestino("MINISTERIO DA SAUDE");
        header.setDataGeracao(LocalDate.now().format(FORMATO_DATA));
        header.setVersaoSistema("01.00");
        header.setVersaoBdsia(competencia + "a");
        return header;
    }

    private PacientePsicossocialDTO mapearPaciente(
            final RaasPsiPacienteProjection p,
            final List<RaasPsiItemProjection> itens) {
        final PacientePsicossocialDTO dto = mapearDadosBasicos(p);
        dto.setAcoes(mapearAcoes(itens));
        return dto;
    }

    private PacientePsicossocialDTO mapearDadosBasicos(
            final RaasPsiPacienteProjection p) {
        final PacientePsicossocialDTO dto = new PacientePsicossocialDTO();
        dto.setUf(formatarUf(p.getUnidadeFederacao()));
        dto.setCompetencia(formatarCompetencia(p.getCompetencia()));
        dto.setCnes(formatarCnes(p.getUnidadePrestadoraServico()));
        dto.setCnsPaciente(formatarCns(p.getCartaoNacionalSaude()));
        dto.setCpfPaciente(p.getCpfPaciente());
        dto.setDataInicio(formatarData(p.getDtInicioValidade()));
        dto.setDataFim(formatarData(p.getDtFinalValidade()));
        dto.setNomePaciente(p.getNmPaciente());
        dto.setNomeMae(p.getNmMae());
        dto.setLogradouro(p.getLogradouro());
        dto.setNumeroEndereco(p.getNumeroLogradouro());
        dto.setComplemento(p.getComplementoLogradouro());
        dto.setCep(p.getCep());
        dto.setMunicipioIbge(formatarMunicipio(p.getMunicipio()));
        dto.setDataNascimento(formatarData(p.getDtNascimento()));
        dto.setSexo(p.getSexo());
        dto.setRacaCor(formatarRaca(p.getRaca()));
        dto.setNomeResponsavel(p.getNmResponsavel());
        dto.setEtnia(formatarEtnia(p.getEtnia()));
        dto.setCelular(p.getCelular());
        dto.setTelefone(p.getTelefone());
        dto.setMotivoSaida(formatarMotivoSaida(p.getMotivoSaidaPermanencia()));
        dto.setCidPrincipal(p.getCidPrincipal());
        dto.setCoberturaEsf(p.getCoberturaEsf() != null ? p.getCoberturaEsf() : "N");
        dto.setCnesEsf(formatarCnes(p.getCodigoCoberturaEsf()));
        dto.setDestinoPaciente(formatarDestino(p.getDestinoPaciente()));
        return dto;
    }

    private List<AcaoPsicossocialDTO> mapearAcoes(
            final List<RaasPsiItemProjection> itens) {
        final List<AcaoPsicossocialDTO> acoes = new ArrayList<>();
        for (final RaasPsiItemProjection i : itens) {
            final AcaoPsicossocialDTO dto = new AcaoPsicossocialDTO();
            dto.setProcedimento(formatarProcedimento(i.getCodProcedimento()));
            dto.setCbo(i.getCodCboExecutante());
            dto.setCnsProfissional(i.getCnsExecutante());
            dto.setDataExecucao(formatarData(i.getDtExecucaoProcedimento()));
            dto.setClassificacao(formatarClassificacao(i.getClassificacao()));
            dto.setQuantidade(i.getQuantidadeRealizada() != null
                    ? i.getQuantidadeRealizada() : 1);
            acoes.add(dto);
        }
        return acoes;
    }

    private String formatarUf(final Integer uf) {
        return uf != null ? String.valueOf(uf) : "52";
    }

    private String formatarCompetencia(final LocalDate competencia) {
        return competencia != null
                ? competencia.format(FORMATO_COMPETENCIA_HEADER) : "";
    }

    private String formatarCnes(final Integer unidade) {
        return unidade != null ? String.valueOf(unidade) : "";
    }

    private String formatarCns(final String cns) {
        return cns != null ? cns.replaceAll("\\D", "") : "";
    }

    private String formatarData(final LocalDate data) {
        return data != null ? data.format(FORMATO_DATA) : "";
    }

    private String formatarMunicipio(final Integer municipio) {
        return municipio != null ? String.valueOf(municipio) : "";
    }

    private String formatarRaca(final Integer raca) {
        return raca != null ? String.format("%02d", raca) : "";
    }

    private String formatarEtnia(final Integer etnia) {
        return etnia != null ? String.valueOf(etnia) : "";
    }

    private String formatarMotivoSaida(final Integer motivo) {
        return motivo != null ? String.format("%02d", motivo) : "00";
    }

    private String formatarDestino(final Integer destino) {
        return destino != null ? String.format("%02d", destino) : "00";
    }

    private String formatarProcedimento(final Long procedimento) {
        return procedimento != null ? String.valueOf(procedimento) : "";
    }

    private String formatarClassificacao(final Integer classificacao) {
        return classificacao != null
                ? String.format("%03d", classificacao) : "001";
    }
}
