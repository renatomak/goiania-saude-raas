package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.HeaderDTO;
import br.gov.goiania.saude.raas.mock.HeaderDTOMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    void gerarHeaderLinha01DeveRetornarHeaderCompletoQuandoDadosValidos() {
        final HeaderDTO header = HeaderDTOMock.valido();
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
            "'ORGÃO', '251415', '12345678000195', 'ORGÃO                         '"
    })
    void gerarHeaderLinha01DeveRetornarNomeResponsavelComPaddingQuandoNomeMenorQue30(
            final String nome, final String sigla, final String cnpj,
            final String expectedNome) {
        final HeaderDTO header = HeaderDTOMock.valido();
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
    void gerarHeaderLinha01DeveRetornarCnpjComZerosQuandoCnpjMenorQue14(
            final String nome, final String sigla, final String cnpj,
            final String expectedCnpj) {
        final HeaderDTO header = HeaderDTOMock.valido();
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
    void gerarHeaderLinha01DeveRetornarQuantidadeFolhasComPaddingQuandoZeroOuGrande(
            final long qtdFolhas, final String expectedQtd) {
        final HeaderDTO header = HeaderDTOMock.valido();
        header.setQuantidadeFolhas(qtdFolhas);
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha.substring(13, 19)).isEqualTo(expectedQtd);
    }

    @Test
    void gerarHeaderLinha01DeveRetornarHeaderComCamposEmBrancoOuZerosQuandoCamposNulosOuVazios() {
        final HeaderDTO header = HeaderDTOMock.vazio();
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha)
                .hasSize(159);
        Assertions.assertThat(linha.substring(23, 53)).isBlank();
        Assertions.assertThat(linha.substring(53, 59)).isBlank();
        Assertions.assertThat(linha.substring(59, 73)).isEqualTo("00000000000000");
    }

    @Test
    void gerarHeaderLinha01DeveRetornarStringComTamanhoExatoQuandoQualquerEntrada() {
        final HeaderDTO header = HeaderDTOMock.minimo();
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha).hasSize(159);
    }

    @Test
    void gerarHeaderLinha01DeveRetornarTamanho159ComCamposNulos() {
        final HeaderDTO header = HeaderDTOMock.nulo();
        final String linha = service.gerarHeaderLinha01(header, 1111L);
        Assertions.assertThat(linha).hasSize(159);
        Assertions.assertThat(linha.substring(0, 2)).isEqualTo("01");
        Assertions.assertThat(linha.substring(2, 7)).isEqualTo("#RAS#");
        Assertions.assertThat(linha.substring(7, 13)).isEqualTo("000000");
        Assertions.assertThat(linha.substring(13, 19)).isEqualTo("000000");
    }
}
