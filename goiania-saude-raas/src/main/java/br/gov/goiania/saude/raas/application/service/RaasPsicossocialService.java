package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaasPsicossocialService {

    private static final int DIVISOR_CONTROLE = 1111;
    public static final String LINE_SEPARATOR = "\r\n";

    private final RaasHeaderService headerService;
    private final RaasPacienteService pacienteService;
    private final RaasAcaoService acaoService;

    public String gerarArquivo(final RaasRemessaPsicossocialDTO remessa) {
        final StringBuilder sb = new StringBuilder();
        final long campoControle = calcularCampoControle(remessa);
        sb.append(headerService.gerarHeaderLinha01(remessa.getHeader(), campoControle));
        sb.append(LINE_SEPARATOR);
        for (final PacientePsicossocialDTO paciente : remessa.getPacientes()) {
            sb.append(pacienteService.gerarLinha15(paciente));
            sb.append(LINE_SEPARATOR);
            for (final AcaoPsicossocialDTO acao : paciente.getAcoes()) {
                sb.append(acaoService.gerarLinha16(paciente, acao));
                sb.append(LINE_SEPARATOR);
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
}
