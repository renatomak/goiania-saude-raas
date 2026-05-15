package br.gov.goiania.saude.raas.application.ports.out;

import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;

public interface BuscarRaasPsicossocialPort {

    RaasRemessaPsicossocialDTO execute(String competencia);
}
