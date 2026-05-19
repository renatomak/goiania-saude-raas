package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.mock.AcaoPsicossocialDTOMock;
import br.gov.goiania.saude.raas.mock.PacientePsicossocialDTOMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RaasAcaoServiceTest {

    private RaasAcaoService service;

    @BeforeEach
    void setUp() {
        service = new RaasAcaoService();
    }

    @Test
    @DisplayName("[Linha 16 - Geral] Deve gerar linha com tamanho exato de 110 caracteres")
    void gerarLinha16DeveRetornarTamanho110QuandoDadosValidos() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final AcaoPsicossocialDTO acao = AcaoPsicossocialDTOMock.valido();
        final String linha = service.gerarLinha16(paciente, acao);
        Assertions.assertThat(linha).hasSize(110);
    }

    @Test
    @DisplayName("[Linha 16 - Campo 01] Deve iniciar com código fixo '16'")
    void gerarLinha16DeveIniciarComCodigo16() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final AcaoPsicossocialDTO acao = AcaoPsicossocialDTOMock.valido();
        final String linha = service.gerarLinha16(paciente, acao);
        Assertions.assertThat(linha.substring(0, 2)).isEqualTo("16");
    }

    @Test
    @DisplayName("[Linha 16 - Campo 07] Código do procedimento SIGTAP deve conter zeros à esquerda")
    void gerarLinha16DevePreencherProcedimentoComZerosAEsquerda() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final AcaoPsicossocialDTO acao = AcaoPsicossocialDTOMock.valido();
        acao.setProcedimento("12345");
        final String linha = service.gerarLinha16(paciente, acao);
        Assertions.assertThat(linha.substring(40, 50)).isEqualTo("0000012345");
    }

    @Test
    @DisplayName("[Linha 16 - Campo 13] Quantidade realizada deve conter zeros à esquerda")
    void gerarLinha16DevePreencherQuantidadeComZerosAEsquerda() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final AcaoPsicossocialDTO acao = AcaoPsicossocialDTOMock.valido();
        acao.setQuantidade(5);
        final String linha = service.gerarLinha16(paciente, acao);
        Assertions.assertThat(linha.substring(85, 91)).isEqualTo("000005");
    }

    @Test
    @DisplayName("[Linha 16 - Campo 14] Deve conter origem das informações 'EXT'")
    void gerarLinha16DeveConterOrigemExt() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final AcaoPsicossocialDTO acao = AcaoPsicossocialDTOMock.valido();
        final String linha = service.gerarLinha16(paciente, acao);
        Assertions.assertThat(linha.substring(91, 94)).isEqualTo("EXT");
    }

    @Test
    @DisplayName("[Linha 16 - Campo 15] Local de realização deve ser 'C' (Caps) ou 'T' (Território)")
    void gerarLinha16DeveConterLocalRealizacao() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final AcaoPsicossocialDTO acao = AcaoPsicossocialDTOMock.valido();
        acao.setLocalRealizacao("C");
        final String linha = service.gerarLinha16(paciente, acao);
        Assertions.assertThat(linha.substring(94, 95)).isEqualTo("C");
    }

    @Test
    @DisplayName("[Linha 16 - Campo 05] Quando CNS informado, CPF deve ser zerado")
    void gerarLinha16DeveZerarCpfQuandoCnsInformado() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        paciente.setCnsPaciente("123456789012345");
        paciente.setCpfPaciente(null);
        final AcaoPsicossocialDTO acao = AcaoPsicossocialDTOMock.valido();
        final String linha = service.gerarLinha16(paciente, acao);
        Assertions.assertThat(linha.substring(17, 32)).isEqualTo("123456789012345");
        Assertions.assertThat(linha.substring(95, 106)).isEqualTo("00000000000");
    }

    @Test
    @DisplayName("[Linha 16 - Campo 17] Deve terminar com filler de 4 espaços")
    void gerarLinha16DeveTerminarComFiller4Espacos() {
        final PacientePsicossocialDTO paciente = PacientePsicossocialDTOMock.valido();
        final AcaoPsicossocialDTO acao = AcaoPsicossocialDTOMock.valido();
        final String linha = service.gerarLinha16(paciente, acao);
        Assertions.assertThat(linha.substring(106, 110)).isEqualTo("    ");
    }
}
