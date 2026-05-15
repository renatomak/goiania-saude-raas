package br.gov.goiania.saude.raas.application.usecase;

import br.gov.goiania.saude.raas.application.ports.in.GerarRaasPsicossocialPortIn;
import br.gov.goiania.saude.raas.application.ports.out.BuscarRaasPsicossocialPort;
import br.gov.goiania.saude.raas.application.service.RaasPsicossocialService;
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
}
