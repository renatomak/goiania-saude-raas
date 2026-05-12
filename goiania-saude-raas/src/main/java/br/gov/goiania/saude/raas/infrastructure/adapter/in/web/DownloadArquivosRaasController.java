package br.gov.goiania.saude.raas.infrastructure.adapter.in.web;

import br.gov.goiania.saude.raas.application.dto.DownloadArquivosRaasResponse;
import br.gov.goiania.saude.raas.application.ports.in.DownloadArquivosRaasUseCasePort;
import br.gov.goiania.saude.raas.infrastructure.adapter.in.web.swagger.DownloadArquivosRaasSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/raas")
@RequiredArgsConstructor
public class DownloadArquivosRaasController implements DownloadArquivosRaasSwagger {

    private final DownloadArquivosRaasUseCasePort downloadRaasUseCase;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<DownloadArquivosRaasResponse> download(
            @PathVariable final Long id) {
        return ResponseEntity.ok(downloadRaasUseCase.execute(id));
    }
}

