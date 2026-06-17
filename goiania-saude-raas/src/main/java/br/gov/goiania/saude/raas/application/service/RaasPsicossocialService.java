package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.Header;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaasPsicossocialService {

    private final RaasHeaderService headerService;
    private final RaasPacienteService pacienteService;
    private final RaasAcaoService acaoService;

    public String gerarArquivo(final RaasRemessaPsicossocialDTO remessa) {
        sincronizarCamposDeControle(remessa);
        final long campoControle = calcularCampoControle(remessa);
        final StringBuilder sb = new StringBuilder();
        sb.append(headerService.gerarHeaderLinha01(remessa.getHeader(), campoControle));
        sb.append("\r\n");
        for (final PacientePsicossocialDTO paciente : remessa.getPacientes()) {
            sb.append(pacienteService.gerarLinha15(paciente));
            sb.append("\r\n");
            for (final AcaoPsicossocialDTO acao : paciente.getAcoes()) {
                sb.append(acaoService.gerarLinha16(paciente, acao));
                sb.append("\r\n");
            }
        }
        return sb.toString();
    }

    private void sincronizarCamposDeControle(final RaasRemessaPsicossocialDTO remessa) {
        if (remessa == null) {
            return;
        }

        final List<PacientePsicossocialDTO> pacientes = remessa.getPacientes() != null
                ? remessa.getPacientes()
                : Collections.emptyList();

        final Header header = remessa.getHeader();
        if (header != null) {
            header.setQuantidadeFolhas((long) pacientes.size());
        }

        for (final PacientePsicossocialDTO paciente : pacientes) {
            if (paciente == null) {
                continue;
            }
            final int totalAcoes = paciente.getAcoes() != null
                    ? paciente.getAcoes().size() : 0;
            paciente.setTotalProcedimentos(totalAcoes);
        }
    }

    private long calcularCampoControle(final RaasRemessaPsicossocialDTO remessa) {
        long subtotalAcoes = 0;
        long subtotalCadastros = 0;
        for (final PacientePsicossocialDTO p : remessa.getPacientes()) {
            subtotalCadastros += parseLongOrZero(p.getCnes());
            subtotalCadastros += parseLongOrZero(p.getCnsPaciente());
            for (final AcaoPsicossocialDTO a : p.getAcoes()) {
                subtotalAcoes += parseLongOrZero(a.getProcedimento());
                subtotalAcoes += a.getQuantidade() != null ? a.getQuantidade() : 0;
            }
        }
        return RaasGenerationDomainService.calcularCampoControle(
                subtotalAcoes, subtotalCadastros);
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
