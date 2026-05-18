package br.gov.goiania.saude.raas.mock;

import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import java.util.Collections;

public final class RaasRemessaPsicossocialDTOMock {

    private RaasRemessaPsicossocialDTOMock() { }

    public static RaasRemessaPsicossocialDTO valido() {
        RaasRemessaPsicossocialDTO dto = new RaasRemessaPsicossocialDTO();
        dto.setHeader(HeaderDTOMock.valido());
        dto.setPacientes(Collections.singletonList(PacientePsicossocialDTOMock.valido()));
        return dto;
    }

    public static RaasRemessaPsicossocialDTO exemplo() {
        RaasRemessaPsicossocialDTO dto = new RaasRemessaPsicossocialDTO();
        dto.setHeader(HeaderDTOMock.exemplo());
        dto.setPacientes(Collections.singletonList(PacientePsicossocialDTOMock.exemplo()));
        return dto;
    }
}

