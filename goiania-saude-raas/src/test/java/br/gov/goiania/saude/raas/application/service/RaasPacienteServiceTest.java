package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.mock.PacientePsicossocialDTOMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RaasPacienteServiceTest {

    private RaasPacienteService service;

    @BeforeEach
    void setUp() {
        service = new RaasPacienteService();
    }

    @Test
    @DisplayName("[Linha 15 - Geral] Deve gerar linha com tamanho exato de 406 caracteres")
    void gerarLinha15DeveRetornarTamanho406QuandoDadosValidos() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha).hasSize(406);
    }

    @Test
    @DisplayName("[Linha 15 - Campo 01] Deve iniciar com código fixo '15'")
    void gerarLinha15DeveIniciarComCodigo15() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha.substring(0, 2)).isEqualTo("15");
    }

    @Test
    @DisplayName("[Linha 15 - Campo 05] Quando CNS informado, deve preencher CNS e CPF como zeros")
    void gerarLinha15DevePreencherCnsQuandoInformado() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setCnsPaciente("123456789012345");
        paciente.setCpfPaciente(null);
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha.substring(17, 32)).isEqualTo("123456789012345");
        Assertions.assertThat(linha.substring(391, 402)).isEqualTo("00000000000");
    }

    @Test
    @DisplayName("[Linha 15 - Campo 05/45] Quando CPF informado, deve preencher CPF e CNS como zeros")
    void gerarLinha15DevePreencherCpfQuandoInformado() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setCnsPaciente(null);
        paciente.setCpfPaciente("12345678901");
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha.substring(17, 32)).isEqualTo("000000000000000");
        Assertions.assertThat(linha.substring(391, 402)).isEqualTo("12345678901");
    }

    @Test
    @DisplayName("[Linha 15 - Campo 05/45] Quando CNS e CPF informados, CNS deve ser priorizado")
    void gerarLinha15DevePriorizarCnsQuandoCnsECpfInformados() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setCnsPaciente("700204928183420");
        paciente.setCpfPaciente(null);

        final String linha = service.gerarLinha15(paciente);

        Assertions.assertThat(linha.substring(17, 32)).isEqualTo("700204928183420");
        Assertions.assertThat(linha.substring(391, 402)).isEqualTo("00000000000");
    }

    @Test
    @DisplayName("[Linha 15 - Campo 15] Município sem DV deve adicionar espaço na última posição")
    void gerarLinha15DeveAdicionarEspacoQuandoMunicipioSemDv() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setMunicipioIbge("123456");
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha.substring(171, 178)).isEqualTo("123456 ");
    }

    @Test
    @DisplayName("[Linha 15 - Campo 21] Etnia deve ser exportada quando raça for Indígena (05)")
    void gerarLinha15DeveExportarEtniaQuandoRacaIndigena() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setRacaCor("05");
        paciente.setEtnia("0001");
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha.substring(222, 226)).isEqualTo("0001");
    }

    @Test
    @DisplayName("[Linha 15 - Campo 40] Tipo de droga deve ser exportado quando usuário for dependente")
    void gerarLinha15DeveExportarTipoDrogaQuandoUsuarioDrogasSim() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setUsuarioDrogas("S");
        paciente.setTipoDrogaAlcool("A");
        paciente.setTipoDrogaCrack("C");
        paciente.setTipoDrogaOutros("O");
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha.substring(301, 302)).isEqualTo("S");
        Assertions.assertThat(linha.substring(302, 303)).isEqualTo("A");
        Assertions.assertThat(linha.substring(303, 304)).isEqualTo("C");
        Assertions.assertThat(linha.substring(304, 305)).isEqualTo("O");
    }

    @Test
    @DisplayName("[Linha 15 - Campo 09] Prontuário deve ser alinhado à esquerda com espaços à direita")
    void gerarLinha15DeveAlinharProntuarioAEsquerdaComEspacos() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setNumeroProntuario("12345");
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha.substring(78, 88)).isEqualTo("12345     ");
    }

    @Test
    @DisplayName("[Linha 15 - Campo 09] Prontuário não deve conter zeros à esquerda")
    void gerarLinha15DeveNaoTerZerosAEsquerdaNoProntuario() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setNumeroProntuario("42");
        final String linha = service.gerarLinha15(paciente);
        final String prontuario = linha.substring(78, 88);
        Assertions.assertThat(prontuario).startsWith("42");
        Assertions.assertThat(prontuario).doesNotStartWith("0");
        Assertions.assertThat(prontuario).isEqualTo("42        ");
    }

    @Test
    @DisplayName("[Linha 15 - Campo 47] Deve terminar com filler de 4 espaços")
    void gerarLinha15DeveTerminarComFiller4Espacos() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final String linha = service.gerarLinha15(paciente);
        Assertions.assertThat(linha.substring(402, 406)).isEqualTo("    ");
    }
}
