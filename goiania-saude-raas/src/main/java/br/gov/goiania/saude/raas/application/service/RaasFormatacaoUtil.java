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
        return leftPadZeros(uf != null ? uf.toString() : "52", 2);
    }

    public static String formatarCompetencia(final LocalDate competencia) {
        if (competencia == null) {
            return "000000";
        }
        return competencia.format(FORMATO_COMPETENCIA);
    }

    public static String formatarCnes(final Integer unidade) {
        return leftPadZeros(unidade != null ? unidade.toString() : "", 7);
    }

    public static String formatarCns(final String cns) {
        final String digits = onlyDigits(cns);
        return leftPadZeros(digits, 15);
    }

    public static String formatarCpf(final String cpf) {
        return onlyDigits(cpf);
    }

    public static String formatarData(final LocalDate data) {
        if (data == null) {
            return "";
        }
        return data.format(FORMATO_DATA);
    }

    public static String formatarMunicipio(final Integer municipio) {
        return leftPadZeros(municipio != null ? municipio.toString() : "", 7);
    }

    public static String formatarRaca(final Integer raca) {
        return raca != null ? String.format("%02d", raca) : "99";
    }

    public static String formatarOrigem(final Integer origem) {
        return origem != null ? String.format("%02d", origem) : "01";
    }

    public static String formatarDestino(final Integer destino) {
        return destino != null ? String.format("%02d", destino) : "00";
    }

    public static String formatarMotivoSaida(final Integer motivo) {
        return motivo != null ? String.format("%02d", motivo) : "00";
    }

    public static String formatarEtnia(final Integer etnia) {
        return etnia != null ? String.valueOf(etnia) : "";
    }

    public static String formatarTipoLogradouro(final Integer tipo) {
        return tipo != null ? String.format("%03d", tipo) : "";
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

    public static String formatarNacionalidade(final Integer nacionalidade) {
        return nacionalidade != null
                ? String.format("%03d", nacionalidade) : "010";
    }

    public static String formatarCarater(final Integer carater) {
        return carater != null ? String.format("%02d", carater) : "";
    }

    public static String formatarCid(final String cid) {
        return cid != null ? cid : "";
    }

    private static String onlyDigits(final String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\D", "");
    }

    private static String leftPadZeros(final String value, final int length) {
        if (value == null || value.isEmpty()) {
            return "0".repeat(length);
        }
        final String numeric = onlyDigits(value);
        if (numeric.length() >= length) {
            return numeric.substring(numeric.length() - length);
        }
        return "0".repeat(length - numeric.length()) + numeric;
    }
}
