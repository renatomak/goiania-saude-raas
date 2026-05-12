package br.gov.goiania.saude.raas.infrastructure.adapter.in.web;

import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasRequest;
import br.gov.goiania.saude.raas.application.dto.ListarArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.ListarArquivosRaasUseCasePort;
import br.gov.goiania.saude.raas.infrastructure.adapter.in.web.swagger.ListarArquivosRaasSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/raas")
@RequiredArgsConstructor
public class ListarArquivosRaasController implements ListarArquivosRaasSwagger {

    private final ListarArquivosRaasUseCasePort listarRaasUseCase;

    @GetMapping
    @Override
    public ResponseEntity<List<ListarArquivosRaasResponse>> listarRaas(
            @RequestParam(required = false) final Integer mes,
            @RequestParam(required = false) final Integer ano,
            @RequestParam(required = false) final String codigoEmpresa,
            @RequestParam(required = false) final Integer situacao) {

        final ListarArquivosRaasRequest request = new ListarArquivosRaasRequest(
                mes, ano, codigoEmpresa, situacao);

        return ResponseEntity.ok(listarRaasUseCase.execute(request));
    }
}
