package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.Header;
import br.gov.goiania.saude.raas.mock.HeaderDTOMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RaasHeaderServiceTest {

    private RaasHeaderService service;

    @BeforeEach
    void setUp() {
        service = new RaasHeaderService();
    }

    @Test
    @DisplayName("[Regra 1-15] Validação geral do header completo (todos os campos)")
    void gerarHeaderLinha01DeveRetornarHeaderCompletoQuandoDadosValidos() {
        final Header header = HeaderDTOMock.valido();
        final String linha = service.gerarHeaderLinha01(header, 1234L);
        Assertions.assertThat(linha)
                .hasSize(159)
                .startsWith(
                        "01#RAS#2024050000121234FATURAMENTO SMS GCPAH         "
                                + "25141512345678000195DESTINO TESTE")
                .contains("20240518")
                .endsWith("               ");
    }

    @ParameterizedTest
    @CsvSource({
            "'SMS', '251415', '12345678000195', 'SMS                           '",
            "'ORGÃO', '251415', '12345678000195', 'ORGAO                         '"
    })
    @DisplayName("[Regra 6] Nome do órgão responsável (cbc-rsp) com padding à direita")
    void gerarHeaderLinha01DeveRetornarNomeResponsavelComPaddingQuandoNomeMenorQue30(
            final String nome, final String sigla, final String cnpj,
            final String expectedNome) {
        final Header header = HeaderDTOMock.valido();
        header.setNomeResponsavel(nome);
        header.setSiglaResponsavel(sigla);
        header.setCnpjResponsavel(cnpj);
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha.substring(23, 53))
                .isEqualTo(expectedNome);
    }

    @ParameterizedTest
    @CsvSource({
            "'FATURAMENTO', '251415', '123', '00000000000123'",
            "'FATURAMENTO', '251415', '', '00000000000000'"
    })
    @DisplayName("[Regra 8] CNPJ do órgão responsável (cbc-cgccpf) com zeros à esquerda")
    void gerarHeaderLinha01DeveRetornarCnpjComZerosQuandoCnpjMenorQue14(
            final String nome, final String sigla, final String cnpj,
            final String expectedCnpj) {
        final Header header = HeaderDTOMock.valido();
        header.setNomeResponsavel(nome);
        header.setSiglaResponsavel(sigla);
        header.setCnpjResponsavel(cnpj);
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha.substring(59, 73)).isEqualTo(expectedCnpj);
    }

    @ParameterizedTest
    @CsvSource({
            "0, '000000'",
            "99999, '099999'"
    })
    @DisplayName("[Regra 4] Quantidade de folhas (cbc-lin) com padding de zeros à esquerda")
    void gerarHeaderLinha01DeveRetornarQuantidadeFolhasComPaddingQuandoZeroOuGrande(
            final long qtdFolhas, final String expectedQtd) {
        final Header header = HeaderDTOMock.valido();
        header.setQuantidadeFolhas(qtdFolhas);
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha.substring(13, 19)).isEqualTo(expectedQtd);
    }

    @Test
    @DisplayName("[Regra 6,7,8] Header com campos em branco ou zeros quando nulos/vazios (cbc-rsp, cbc-sgl, cbc-cgccpf)")
    void gerarHeaderLinha01DeveRetornarHeaderComCamposEmBrancoOuZerosQuandoCamposNulosOuVazios() {
        final Header header = HeaderDTOMock.vazio();
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha)
                .hasSize(159);
        Assertions.assertThat(linha.substring(23, 53)).isBlank();
        Assertions.assertThat(linha.substring(53, 59)).isBlank();
        Assertions.assertThat(linha.substring(59, 73)).isEqualTo("00000000000000");
    }

    @Test
    @DisplayName("[Regra 1-15] Header com tamanho exato (159 caracteres)")
    void gerarHeaderLinha01DeveRetornarStringComTamanhoExatoQuandoQualquerEntrada() {
        final Header header = HeaderDTOMock.minimo();
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha).hasSize(159);
    }

    @Test
    @DisplayName("[Regra 1-4] Header com campos fixos e tamanho correto quando campos nulos")
    void gerarHeaderLinha01DeveRetornarTamanho159ComCamposNulos() {
        final Header header = HeaderDTOMock.nulo();
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha).hasSize(159);
        Assertions.assertThat(linha.substring(0, 2)).isEqualTo("01");
        Assertions.assertThat(linha.substring(2, 7)).isEqualTo("#RAS#");
        Assertions.assertThat(linha.substring(7, 13)).isEqualTo("000000");
        Assertions.assertThat(linha.substring(13, 19)).isEqualTo("000000");
    }
}
