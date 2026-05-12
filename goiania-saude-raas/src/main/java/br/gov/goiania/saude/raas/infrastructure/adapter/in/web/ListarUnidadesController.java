package br.gov.goiania.saude.raas.infrastructure.adapter.in.web;

import br.gov.goiania.saude.raas.application.dto.ListarUnidadesResponse;
import br.gov.goiania.saude.raas.application.ports.in.ListarUnidadesUseCasePort;
import br.gov.goiania.saude.raas.infrastructure.adapter.in.web.swagger.ListarUnidadesSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/unidades")
@RequiredArgsConstructor
public class ListarUnidadesController implements ListarUnidadesSwagger {

    private final ListarUnidadesUseCasePort useCasePort;

    @Override
    public ResponseEntity<List<ListarUnidadesResponse>> listarTodasAsUnidades() {
        return ResponseEntity.ok(useCasePort.execute());
    }
}

