package br.gov.goiania.saude.raas.infrastructure.adapter.in.web;

import br.gov.goiania.saude.raas.application.ports.in.GerarRaasPsicossocialPortIn;
import br.gov.goiania.saude.raas.infrastructure.adapter.in.web.swagger.GerarRaasPsicossocialSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/raas/psicossocial")
@RequiredArgsConstructor
public class GerarRaasPsicossocialController implements GerarRaasPsicossocialSwagger {

    private final GerarRaasPsicossocialPortIn useCase;

    @GetMapping(value = "/gerar/{mes}/{ano}", produces = MediaType.TEXT_PLAIN_VALUE)
    @Override
    public ResponseEntity<String> gerar(@PathVariable final Integer mes,
                                         @PathVariable final Integer ano) {
        final String conteudo = useCase.execute(mes, ano);
        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(conteudo);
    }

    @GetMapping(value = "/gerar/{mes}/{ano}/{cdRaasPsi}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> gerarPaciente(
            @PathVariable final Integer mes,
            @PathVariable final Integer ano,
            @PathVariable final Long cdRaasPsi) {
        final String conteudo = useCase.executePaciente(mes, ano, cdRaasPsi);
        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(conteudo);
    }
}
