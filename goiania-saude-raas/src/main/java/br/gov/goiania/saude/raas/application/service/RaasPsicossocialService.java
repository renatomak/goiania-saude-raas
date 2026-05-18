package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.HeaderDTO;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import org.springframework.stereotype.Service;

@Service
public class RaasPsicossocialService {

    private static final int TAMANHO_LINHA_01 = 159;
    private static final int DIVISOR_CONTROLE = 1111;

    public String gerarArquivo(final RaasRemessaPsicossocialDTO remessa) {
        final StringBuilder sb = new StringBuilder();

        final long campoControle = calcularCampoControle(remessa);
        sb.append(gerarLinha01(remessa.getHeader(), campoControle));
        sb.append("\r\n");

        for (final PacientePsicossocialDTO paciente : remessa.getPacientes()) {
            sb.append(gerarLinha15(paciente));
            sb.append("\r\n");
            for (final AcaoPsicossocialDTO acao : paciente.getAcoes()) {
                sb.append(gerarLinha16(paciente, acao));
                sb.append("\r\n");
            }
        }
        return sb.toString();
    }

    private long calcularCampoControle(final RaasRemessaPsicossocialDTO remessa) {
        long soma = 0;
        for (final PacientePsicossocialDTO p : remessa.getPacientes()) {
            soma += parseLongOrZero(p.getCnes());
            soma += parseLongOrZero(p.getCnsPaciente());
            for (final AcaoPsicossocialDTO a : p.getAcoes()) {
                soma += parseLongOrZero(a.getProcedimento());
                soma += a.getQuantidade() != null ? a.getQuantidade() : 0;
            }
        }
        return DIVISOR_CONTROLE + (soma % DIVISOR_CONTROLE);
    }

    private long parseLongOrZero(final String value) {
        if (value == null) {
            return 0;
        }
        try {
            return Long.parseLong(value.replaceAll("\\D", ""));
        } catch (final NumberFormatException e) {
            return 0;
        }
    }

    private String gerarLinha01(final HeaderDTO header, final long campoControle) {
        final StringBuilder sb = new StringBuilder();
        sb.append("01");                                           // 001-002
        sb.append("#RAS#");                                        // 003-007
        sb.append(leftPad(header.getCompetencia(), 6));            // 008-013
        sb.append(leftPad(String.valueOf(header.getQuantidadeFolhas()), 6)); // 014-019
        sb.append(String.format("%04d", campoControle));           // 020-023
        sb.append(rightPad(header.getNomeResponsavel(), 30));      // 024-053
        sb.append(rightPad(header.getSiglaResponsavel(), 6));      // 054-059
        sb.append(leftPad(numericOnly(header.getCnpjResponsavel()), 14)); // 060-073
        sb.append(rightPad(header.getNomeDestino(), 40));          // 074-113
        sb.append("M");                                            // 114
        sb.append(header.getDataGeracao());                        // 115-122
        sb.append(rightPad(header.getVersaoSistema(), 5));         // 123-127
        sb.append("          ");                                   // 128-137
        sb.append(rightPad(header.getVersaoBdsia(), 7));           // 138-144
        sb.append("               ");                              // 145-159

        final String linha = sb.toString();
        if (linha.length() != TAMANHO_LINHA_01) {
            throw new IllegalStateException(
                    "Linha 01 com tamanho " + linha.length()
                            + ", esperado " + TAMANHO_LINHA_01);
        }
        return linha;
    }

    private String gerarLinha15(final PacientePsicossocialDTO paciente) {
        final StringBuilder sb = new StringBuilder();
        sb.append(gerarLinha15Parte1(paciente));
        sb.append(gerarLinha15Parte2(paciente));
        sb.append(gerarLinha15Parte3(paciente));
        return sb.toString();
    }

    private String gerarLinha15Parte1(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append("15");
        sb.append(leftPad(p.getUf(), 2));
        sb.append(leftPad(p.getCompetencia(), 6));
        sb.append(leftPad(p.getCnes(), 7));
        sb.append(rightPad(p.getCnsPaciente(), 15));
        sb.append(rightPad(p.getDataInicio(), 8));
        sb.append(rightPad(p.getDataFim(), 8));
        sb.append(rightPad(p.getNomePaciente(), 30));
        sb.append(rightPad(p.getNumeroProntuario(), 10));
        sb.append(rightPad(p.getNomeMae(), 30));
        sb.append(rightPad(p.getLogradouro(), 30));
        sb.append(rightPad(p.getNumeroEndereco(), 5));
        sb.append(rightPad(p.getComplemento(), 10));
        sb.append(rightPad(p.getCep(), 8));
        sb.append(rightPad(p.getMunicipioIbge(), 7));
        sb.append(rightPad(p.getDataNascimento(), 8));
        sb.append(p.getSexo());
        sb.append(rightPad(p.getRacaCor(), 2));
        sb.append(rightPad(p.getNomeResponsavel(), 30));
        sb.append("010");
        sb.append(rightPad(p.getEtnia(), 4));
        sb.append(rightPad(p.getTelefone(), 11));
        sb.append(rightPad(p.getCelular(), 11));
        sb.append(p.getMotivoSaida());
        sb.append("        ");
        sb.append(rightPad(p.getCidPrincipal(), 4));
        return sb.toString();
    }

    private String gerarLinha15Parte2(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append("    ");
        sb.append("    ");
        sb.append("    ");
        sb.append("    ");
        sb.append("  ");
        sb.append(p.getOrigemPaciente());
        sb.append(p.getCoberturaEsf());
        sb.append(rightPad(p.getCnesEsf(), 7));
        sb.append(leftPad(String.valueOf(p.getAcoes().size()), 5));
        sb.append(p.getDestinoPaciente());
        sb.append("EXT");
        sb.append(p.getSituacaoRua());
        sb.append(p.getUsuarioDrogas());
        sb.append(rightPad(p.getTipoDrogaAlcool(), 1));
        sb.append(rightPad(p.getTipoDrogaCrack(), 1));
        sb.append(rightPad(p.getTipoDrogaOutros(), 1));
        return sb.toString();
    }

    private String gerarLinha15Parte3(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append("             ");
        sb.append(rightPad(p.getDescricaoBairro(), 30));
        sb.append(rightPad(p.getTipoLogradouro(), 3));
        sb.append(rightPad(p.getEmailPaciente(), 40));
        sb.append(leftPad(p.getCpfPaciente(), 11));
        sb.append("    ");
        return sb.toString();
    }

    private String gerarLinha16(final PacientePsicossocialDTO paciente,
                                final AcaoPsicossocialDTO acao) {
        final StringBuilder sb = new StringBuilder();
        sb.append("16");                                           // 001-002
        sb.append(leftPad(paciente.getUf(), 2));                   // 003-004
        sb.append(leftPad(paciente.getCompetencia(), 6));          // 005-010
        sb.append(leftPad(paciente.getCnes(), 7));                 // 011-017
        sb.append(rightPad(paciente.getCnsPaciente(), 15));        // 018-032
        sb.append(rightPad(paciente.getDataInicio(), 8));          // 033-040
        sb.append(leftPad(acao.getProcedimento(), 10));            // 041-050
        sb.append(leftPad(acao.getCbo(), 6));                      // 051-056
        sb.append(rightPad(acao.getCnsProfissional(), 15));        // 057-071
        sb.append(rightPad(acao.getDataExecucao(), 8));            // 072-079
        sb.append(rightPad(acao.getServico(), 3));                 // 080-082
        sb.append(rightPad(acao.getClassificacao(), 3));           // 083-085
        sb.append(leftPad(acao.getQuantidade().toString(), 6));    // 086-091
        sb.append("EXT");                                          // 092-094
        sb.append(acao.getLocalRealizacao());                      // 095
        sb.append(leftPad(paciente.getCpfPaciente(), 11));         // 096-106
        sb.append("    ");                                         // 107-110
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
