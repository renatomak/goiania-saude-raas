package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.HeaderDTO;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import org.springframework.stereotype.Service;

@Service
public class RaasPsicossocialService {

    private static final String FILLER_10 = "          ";
    private static final String FILLER_13 = "             ";
    private static final String FILLER_30 = "                              ";
    private static final String FILLER_40 = "                                        ";
    private static final String FILLER_4 = "    ";
    private static final String FILLER_3 = "   ";
    private static final String FILLER_2 = "  ";
    private static final String FILLER_8 = "        ";

    public String gerarArquivo(final RaasRemessaPsicossocialDTO remessa) {
        final StringBuilder sb = new StringBuilder();
        sb.append(gerarLinha01(remessa.getHeader()));
        sb.append("\r\n");
        for (final PacientePsicossocialDTO paciente : remessa.getPacientes()) {
            sb.append(gerarLinha15(paciente));
            sb.append(gerarLinha15Continuacao(paciente));
            sb.append("\r\n");
            for (final AcaoPsicossocialDTO acao : paciente.getAcoes()) {
                sb.append(gerarLinha16(paciente, acao));
                sb.append("\r\n");
            }
        }
        return sb.toString();
    }

    private String gerarLinha01(final HeaderDTO header) {
        final StringBuilder sb = new StringBuilder();
        sb.append("01");
        sb.append("#RAS#");
        sb.append(leftPad(header.getCompetencia(), 6));
        sb.append(leftPad(String.valueOf(header.getQuantidadeFolhas()), 6));
        sb.append(String.valueOf(header.getCampoControle()));
        sb.append(rightPad(header.getNomeResponsavel(), 30));
        sb.append(rightPad(header.getSiglaResponsavel(), 6));
        sb.append(leftPad(numericOnly(header.getCnpjResponsavel()), 14));
        sb.append(rightPad(header.getNomeDestino(), 40));
        sb.append("M");
        sb.append(header.getDataGeracao());
        sb.append(rightPad(header.getVersaoSistema(), 5));
        sb.append(FILLER_10);
        sb.append(rightPad(header.getVersaoBdsia() != null ? header.getVersaoBdsia() : "", 7));
        sb.append("               ");
        return sb.toString();
    }

    private String gerarLinha15(final PacientePsicossocialDTO paciente) {
        final StringBuilder sb = new StringBuilder();
        sb.append("15");
        sb.append(leftPad(paciente.getUf(), 2));
        sb.append(leftPad(paciente.getCompetencia(), 6));
        sb.append(leftPad(paciente.getCnes(), 7));
        sb.append(rightPad(paciente.getCnsPaciente() != null ? paciente.getCnsPaciente() : "", 15));
        sb.append(rightPad(paciente.getDataInicio(), 8));
        sb.append(rightPad(paciente.getDataFim() != null ? paciente.getDataFim() : "", 8));
        sb.append(rightPad(paciente.getNomePaciente(), 30));
        sb.append(rightPad(paciente.getNumeroProntuario(), 10));
        sb.append(rightPad(paciente.getNomeMae(), 30));
        sb.append(rightPad(paciente.getLogradouro(), 30));
        sb.append(rightPad(paciente.getNumeroEndereco(), 5));
        sb.append(rightPad(paciente.getComplemento(), 10));
        sb.append(rightPad(paciente.getCep(), 8));
        sb.append(rightPad(paciente.getMunicipioIbge(), 7));
        sb.append(rightPad(paciente.getDataNascimento(), 8));
        sb.append(paciente.getSexo());
        sb.append(rightPad(paciente.getRacaCor(), 2));
        sb.append(rightPad(paciente.getNomeResponsavel(), 30));
        sb.append("010");
        sb.append(rightPad(paciente.getEtnia() != null ? paciente.getEtnia() : "", 4));
        sb.append(rightPad(paciente.getTelefone() != null ? paciente.getTelefone() : "", 11));
        sb.append(rightPad(paciente.getCelular() != null ? paciente.getCelular() : "", 11));
        sb.append(paciente.getMotivoSaida());
        sb.append(FILLER_8);
        sb.append(rightPad(paciente.getCidPrincipal(), 4));
        return sb.toString();
    }

    private String gerarLinha15Continuacao(final PacientePsicossocialDTO paciente) {
        final StringBuilder sb = new StringBuilder();
        sb.append(FILLER_4);
        sb.append(FILLER_4);
        sb.append(FILLER_4);
        sb.append(FILLER_4);
        sb.append(FILLER_2);
        sb.append(paciente.getOrigemPaciente());
        sb.append(paciente.getCoberturaEsf());
        sb.append(rightPad(paciente.getCnesEsf() != null ? paciente.getCnesEsf() : "", 7));
        sb.append(leftPad(String.valueOf(paciente.getAcoes().size()), 5));
        sb.append(paciente.getDestinoPaciente());
        sb.append("EXT");
        sb.append(paciente.getSituacaoRua());
        sb.append(paciente.getUsuarioDrogas());
        sb.append(rightPad(paciente.getTipoDrogaAlcool(), 1));
        sb.append(rightPad(paciente.getTipoDrogaCrack(), 1));
        sb.append(rightPad(paciente.getTipoDrogaOutros(), 1));
        sb.append(FILLER_13);
        sb.append(rightPad(paciente.getDescricaoBairro(), 30));
        sb.append(rightPad(paciente.getTipoLogradouro(), 3));
        sb.append(rightPad(paciente.getEmailPaciente(), 40));
        sb.append(leftPad(paciente.getCpfPaciente() != null ? paciente.getCpfPaciente() : "", 11));
        sb.append(FILLER_4);
        return sb.toString();
    }

    private String gerarLinha16(final PacientePsicossocialDTO paciente,
                                final AcaoPsicossocialDTO acao) {
        final StringBuilder sb = new StringBuilder();
        sb.append("16");
        sb.append(leftPad(paciente.getUf(), 2));
        sb.append(leftPad(paciente.getCompetencia(), 6));
        sb.append(leftPad(paciente.getCnes(), 7));
        sb.append(rightPad(paciente.getCnsPaciente() != null ? paciente.getCnsPaciente() : "", 15));
        sb.append(rightPad(paciente.getDataInicio(), 8));
        sb.append(leftPad(acao.getProcedimento(), 10));
        sb.append(leftPad(acao.getCbo(), 6));
        sb.append(rightPad(acao.getCnsProfissional(), 15));
        sb.append(rightPad(acao.getDataExecucao(), 8));
        sb.append(rightPad(acao.getServico(), 3));
        sb.append(rightPad(acao.getClassificacao() != null ? acao.getClassificacao() : "001", 3));
        sb.append(leftPad(acao.getQuantidade().toString(), 6));
        sb.append("EXT");
        sb.append(acao.getLocalRealizacao());
        sb.append(leftPad(paciente.getCpfPaciente() != null ? paciente.getCpfPaciente() : "", 11));
        sb.append(FILLER_4);
        return sb.toString();
    }

    private String leftPad(final String value, final int length) {
        if (value == null) {
            return " ".repeat(length);
        }
        final String numeric = numericOnly(value);
        if (numeric.length() >= length) {
            return numeric.substring(numeric.length() - length);
        }
        return "0".repeat(length - numeric.length()) + numeric;
    }

    private String rightPad(final String value, final int length) {
        if (value == null) {
            return " ".repeat(length);
        }
        if (value.length() >= length) {
            return value.substring(0, length);
        }
        return value + " ".repeat(length - value.length());
    }

    private String numericOnly(final String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\D", "");
    }
}
