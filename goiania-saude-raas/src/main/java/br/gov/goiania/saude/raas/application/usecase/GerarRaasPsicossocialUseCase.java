package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import br.gov.goiania.saude.raas.application.ports.in.GerarRaasPsicossocialPortIn;
import br.gov.goiania.saude.raas.application.ports.out.BuscarRaasPsicossocialPort;
import br.gov.goiania.saude.raas.application.service.RaasPsicossocialService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GerarRaasPsicossocialUseCase implements GerarRaasPsicossocialPortIn {

    private final BuscarRaasPsicossocialPort buscarPort;
    private final RaasPsicossocialService service;

    @Override
    public String execute(final Integer mes, final Integer ano) {
        final String competencia = String.format("%04d%02d", ano, mes);
        return service.gerarArquivo(buscarPort.execute(competencia));
    }

    @Override
    public String executePaciente(final Integer mes, final Integer ano,
                                   final Long cdRaasPsi) {
        final String competencia = String.format("%04d%02d", ano, mes);
        final RaasRemessaPsicossocialDTO remessa =
                buscarPort.execute(competencia);

        final PacientePsicossocialDTO paciente = remessa.getPacientes().stream()
                .filter(p -> p.getCnsPaciente().equals(cdRaasPsi.toString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Paciente nao encontrado: " + cdRaasPsi));

        final RaasRemessaPsicossocialDTO remessaFiltrada =
                new RaasRemessaPsicossocialDTO();
        remessaFiltrada.setHeader(remessa.getHeader());
        remessaFiltrada.setPacientes(Collections.singletonList(paciente));

        return service.gerarArquivo(remessaFiltrada);
    }
}
