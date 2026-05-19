package br.gov.goiania.saude.raas.application.dto;

import java.util.List;
import lombok.Data;

@Data
public class RaasRemessaPsicossocialDTO {
    private Header header;
    private List<PacientePsicossocialDTO> pacientes;
}
