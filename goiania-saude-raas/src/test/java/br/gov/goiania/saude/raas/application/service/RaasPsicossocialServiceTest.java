package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import br.gov.goiania.saude.raas.mock.RaasRemessaPsicossocialDTOMock;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RaasPsicossocialServiceTest {

    private RaasPsicossocialService service;

    @BeforeEach
    void setUp() {
        service = new RaasPsicossocialService(
                new RaasHeaderService(),
                new RaasPacienteService(),
                new RaasAcaoService());
    }

    @Test
    void gerarArquivoDeveRetornarConteudoCompletoQuandoRemessaValida() {
        final RaasRemessaPsicossocialDTO remessa =
                RaasRemessaPsicossocialDTOMock.valido();
        final String resultado = service.gerarArquivo(remessa);
        Assertions.assertThat(resultado)
                .isNotNull()
                .startsWith("01#RAS#");
    }

    @Test
    void gerarArquivoDeveCalcularCampoControleQuandoRemessaComPacientes() {
        final RaasRemessaPsicossocialDTO remessa =
                RaasRemessaPsicossocialDTOMock.valido();
        remessa.getPacientes().get(0).setAcoes(
                Collections.singletonList(
                        br.gov.goiania.saude.raas.mock.AcaoPsicossocialDTOMock.valido()));
        final String resultado = service.gerarArquivo(remessa);
        Assertions.assertThat(resultado)
                .isNotNull()
                .contains("\r\n");
    }
}
