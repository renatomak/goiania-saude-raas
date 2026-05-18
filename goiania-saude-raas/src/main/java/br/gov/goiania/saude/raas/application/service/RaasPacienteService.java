package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import org.springframework.stereotype.Service;

@Service
public class RaasPacienteService {

    public String gerarLinha15(final PacientePsicossocialDTO paciente) {
        final StringBuilder sb = new StringBuilder();
        sb.append(gerarParte1(paciente));
        sb.append(gerarParte2(paciente));
        sb.append(gerarParte3(paciente));
        return sb.toString();
    }

    private String gerarParte1(final PacientePsicossocialDTO p) {
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

    private String gerarParte2(final PacientePsicossocialDTO p) {
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

    private String gerarParte3(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append("             ");
        sb.append(rightPad(p.getDescricaoBairro(), 30));
        sb.append(rightPad(p.getTipoLogradouro(), 3));
        sb.append(rightPad(p.getEmailPaciente(), 40));
        sb.append(leftPad(p.getCpfPaciente(), 11));
        sb.append("    ");
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
