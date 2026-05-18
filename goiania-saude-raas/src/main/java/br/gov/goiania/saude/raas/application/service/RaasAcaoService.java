package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import org.springframework.stereotype.Service;

@Service
public class RaasAcaoService {

    public String gerarLinha16(final PacientePsicossocialDTO paciente,
                               final AcaoPsicossocialDTO acao) {
        final StringBuilder sb = new StringBuilder();
        sb.append("16");
        sb.append(leftPad(paciente.getUf(), 2));
        sb.append(leftPad(paciente.getCompetencia(), 6));
        sb.append(leftPad(paciente.getCnes(), 7));
        sb.append(rightPad(paciente.getCnsPaciente(), 15));
        sb.append(rightPad(paciente.getDataInicio(), 8));
        sb.append(leftPad(acao.getProcedimento(), 10));
        sb.append(leftPad(acao.getCbo(), 6));
        sb.append(rightPad(acao.getCnsProfissional(), 15));
        sb.append(rightPad(acao.getDataExecucao(), 8));
        sb.append(rightPad(acao.getServico(), 3));
        sb.append(rightPad(acao.getClassificacao(), 3));
        sb.append(leftPad(acao.getQuantidade().toString(), 6));
        sb.append("EXT");
        sb.append(acao.getLocalRealizacao());
        sb.append(leftPad(paciente.getCpfPaciente(), 11));
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
