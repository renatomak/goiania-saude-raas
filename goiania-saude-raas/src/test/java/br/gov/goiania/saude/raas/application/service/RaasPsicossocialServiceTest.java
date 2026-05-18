package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.HeaderDTO;
import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import br.gov.goiania.saude.raas.mock.HeaderDTOMock;
import br.gov.goiania.saude.raas.mock.RaasRemessaPsicossocialDTOMock;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RaasPsicossocialServiceTest {

    private RaasPsicossocialService service;

    @BeforeEach
    void setUp() {
        service = new RaasPsicossocialService();
    }

    @Test
    void gerarLinha01DeveRetornarHeaderCompletoQuandoDadosValidos() {
        final HeaderDTO header = HeaderDTOMock.valido();
        final String linha = service.gerarLinha01(header, 1234L);
        Assertions.assertThat(linha)
                .hasSize(159)
                .startsWith(
                        "01#RAS#2024050000121234FATURAMENTO SMS GCPAH         25141512345678000195DESTINO TESTE")
                .contains("20240518")
                .endsWith("               ");
    }

    @ParameterizedTest
    @CsvSource({
            "'SMS', '251415', '12345678000195', 'SMS                           '",
            "'ORGÃO', '251415', '12345678000195', 'ORGÃO                         '"
    })
    void gerarLinha01DeveRetornarNomeResponsavelComPaddingQuandoNomeMenorQue30(
            final String nome, final String sigla, final String cnpj,
            final String expectedNome) {
        final HeaderDTO header = HeaderDTOMock.valido();
        header.setNomeResponsavel(nome);
        header.setSiglaResponsavel(sigla);
        header.setCnpjResponsavel(cnpj);
        final String linha = service.gerarLinha01(header, 1111L);
        Assertions.assertThat(linha.substring(23, 53))
                .isEqualTo(expectedNome);
    }

    @ParameterizedTest
    @CsvSource({
            "'FATURAMENTO', '251415', '123', '00000000000123'",
            "'FATURAMENTO', '251415', '', '00000000000000'"
    })
    void gerarLinha01DeveRetornarCnpjComZerosQuandoCnpjMenorQue14(
            final String nome, final String sigla, final String cnpj,
            final String expectedCnpj) {
        final HeaderDTO header = HeaderDTOMock.valido();
        header.setNomeResponsavel(nome);
        header.setSiglaResponsavel(sigla);
        header.setCnpjResponsavel(cnpj);
        final String linha = service.gerarLinha01(header, 1111L);
        Assertions.assertThat(linha.substring(59, 73)).isEqualTo(expectedCnpj);
    }

    @ParameterizedTest
    @CsvSource({
            "0, '000000'",
            "99999, '099999'"
    })
    void gerarLinha01DeveRetornarQuantidadeFolhasComPaddingQuandoZeroOuGrande(
            final long qtdFolhas, final String expectedQtd) {
        final HeaderDTO header = HeaderDTOMock.valido();
        header.setQuantidadeFolhas(qtdFolhas);
        final String linha = service.gerarLinha01(header, 1111L);
        Assertions.assertThat(linha.substring(13, 19)).isEqualTo(expectedQtd);
    }

    @Test
    void gerarLinha01DeveRetornarHeaderComCamposEmBrancoOuZerosQuandoCamposNulosOuVazios() {
        final HeaderDTO header = new HeaderDTO();
        header.setCompetencia("202405");
        header.setQuantidadeFolhas(0L);
        header.setDataGeracao("20240518");
        header.setVersaoSistema("02.21");
        header.setVersaoBdsia("202405a");
        final String linha = service.gerarLinha01(header, 1111L);
        Assertions.assertThat(linha)
                .hasSize(159);
        Assertions.assertThat(linha.substring(23, 53)).isBlank();
        Assertions.assertThat(linha.substring(53, 59)).isBlank();
        Assertions.assertThat(linha.substring(59, 73)).isEqualTo("00000000000000");
    }

    @Test
    void gerarLinha01DeveRetornarStringComTamanhoExatoQuandoQualquerEntrada() {
        final HeaderDTO header = HeaderDTOMock.exemplo();
        header.setCompetencia("1");
        header.setQuantidadeFolhas(1L);
        header.setNomeResponsavel("A");
        header.setSiglaResponsavel("B");
        header.setCnpjResponsavel("1");
        header.setNomeDestino("C");
        header.setDataGeracao("20240518");
        header.setVersaoSistema("1.0.0");
        header.setVersaoBdsia("2.0.0");
        final String linha = service.gerarLinha01(header, 1111L);
        Assertions.assertThat(linha).hasSize(159);
    }

    @Test
    void gerarLinha01DeveLancarExcecaoQuandoTamanhoDiferenteDe159() {
        final HeaderDTO header = HeaderDTOMock.exemplo();
        header.setCompetencia(null);
        header.setQuantidadeFolhas(null);
        header.setNomeResponsavel(null);
        header.setSiglaResponsavel(null);
        header.setCnpjResponsavel(null);
        header.setNomeDestino(null);
        header.setDataGeracao(null);
        header.setVersaoSistema(null);
        header.setVersaoBdsia(null);
        Assertions.assertThatThrownBy(() -> service.gerarLinha01(header, 1111L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Linha 01 com tamanho");
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
