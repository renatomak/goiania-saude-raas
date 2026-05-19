package br.gov.goiania.saude.raas.application.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class RaasFormatacaoUtil {

    private RaasFormatacaoUtil() { }

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter FORMATO_COMPETENCIA =
            DateTimeFormatter.ofPattern("yyyyMM");

    public static String defaultString(final String value,
                                        final String defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static String formatarUf(final Integer uf) {
        return uf != null ? String.valueOf(uf) : "52";
    }

    public static String formatarCompetencia(final LocalDate competencia) {
        return competencia != null
                ? competencia.format(FORMATO_COMPETENCIA) : "";
    }

    public static String formatarCnes(final Integer unidade) {
        return unidade != null ? String.valueOf(unidade) : "";
    }

    public static String formatarCns(final String cns) {
        return cns != null ? cns.replaceAll("\\D", "") : "";
    }

    public static String formatarData(final LocalDate data) {
        return data != null ? data.format(FORMATO_DATA) : "";
    }

    public static String formatarMunicipio(final Integer municipio) {
        return municipio != null ? String.valueOf(municipio) : "";
    }

    public static String formatarRaca(final Integer raca) {
        return raca != null ? String.format("%02d", raca) : "";
    }

    public static String formatarEtnia(final Integer etnia) {
        return etnia != null ? String.valueOf(etnia) : "";
    }

    public static String formatarMotivoSaida(final Integer motivo) {
        return motivo != null ? String.format("%02d", motivo) : "00";
    }

    public static String formatarDestino(final Integer destino) {
        return destino != null ? String.format("%02d", destino) : "00";
    }

    public static String formatarProcedimento(final Long procedimento) {
        return procedimento != null ? String.valueOf(procedimento) : "";
    }

    public static String formatarClassificacao(final Integer classificacao) {
        return classificacao != null
                ? String.format("%03d", classificacao) : "001";
    }

    public static String formatarServico(final Integer servico) {
        return servico != null ? String.format("%03d", servico) : "113";
    }

    public static String formatarOrigem(final Integer origem) {
        return origem != null ? String.format("%02d", origem) : "01";
    }

    public static String formatarTipoLogradouro(final Integer tipo) {
        return tipo != null ? String.format("%03d", tipo) : "";
    }
}
