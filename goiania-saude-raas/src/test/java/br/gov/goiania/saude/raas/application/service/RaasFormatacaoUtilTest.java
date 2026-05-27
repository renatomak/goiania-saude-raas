package br.gov.goiania.saude.raas.application.service;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RaasFormatacaoUtilTest {

    @Test
    @DisplayName("formatarCnes deve respeitar 7 digitos com zeros a esquerda")
    void formatarCnesDeveRespeitarSeteDigitos() {
        Assertions.assertThat(RaasFormatacaoUtil.formatarCnes(3024504))
                .isEqualTo("3024504");
        Assertions.assertThat(RaasFormatacaoUtil.formatarCnes(12345))
                .isEqualTo("0012345");
        Assertions.assertThat(RaasFormatacaoUtil.formatarCnes(null))
                .isEqualTo("0000000");
    }

    @Test
    @DisplayName("formatarCns deve limpar mascara e manter 15 posicoes")
    void formatarCnsDeveLimparMascaraEPadronizarTamanho() {
        Assertions.assertThat(RaasFormatacaoUtil.formatarCns("700.2049.2818.3420"))
                .isEqualTo("700204928183420");
        Assertions.assertThat(RaasFormatacaoUtil.formatarCns("12345678901234"))
                .isEqualTo("012345678901234");
        Assertions.assertThat(RaasFormatacaoUtil.formatarCns(null))
                .isEqualTo("000000000000000");
    }

    @Test
    @DisplayName("formatarCompetencia e formatarData devem seguir padrao oficial")
    void formatarCompetenciaEDataDevemSeguirPadrao() {
        final LocalDate data = LocalDate.of(2026, 5, 1);

        Assertions.assertThat(RaasFormatacaoUtil.formatarCompetencia(data))
                .isEqualTo("202605");
        Assertions.assertThat(RaasFormatacaoUtil.formatarData(data))
                .isEqualTo("20260501");
        Assertions.assertThat(RaasFormatacaoUtil.formatarCompetencia(null))
                .isEqualTo("000000");
        Assertions.assertThat(RaasFormatacaoUtil.formatarData(null))
                .isEqualTo("");
    }
}

