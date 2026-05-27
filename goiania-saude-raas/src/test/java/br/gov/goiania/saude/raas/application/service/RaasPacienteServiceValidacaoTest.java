package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.mock.PacientePsicossocialDTOMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RaasPacienteServiceValidacaoTest {

    private RaasPacienteService service;

    @BeforeEach
    void setUp() {
        service = new RaasPacienteService();
    }

    @Test
    @DisplayName("Deve gerar resultado com tamanho exato de 406 caracteres")
    void validarTamanhoeEspacamentoLinha15() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setCnsPaciente("700204928183420");
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha).hasSize(406);
        Assertions.assertThat(linha).startsWith("15");
        Assertions.assertThat(linha).doesNotContain("SILVAGESSIANA");
        Assertions.assertThat(linha).doesNotContain("ARANJEIRASNI");
        System.out.println("✓ Linha 15 gerada com 406 bytes (com spacing correto)");
    }

    @Test
    @DisplayName("Deve posicionar CNS nas posições 17-31")
    void validarPosicionamentoExatoCampos() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setCnsPaciente("700204928183420");
        final String linha = service.gerarLinha15(paciente);
        final String cnsField = linha.substring(17, 32);
        Assertions.assertThat(cnsField).isEqualTo("700204928183420");
        System.out.println("✓ CNS posicionado corretamente nas posições 17-31");
    }
}

