package br.gov.goiania.saude.raas.application.service;

import java.text.Normalizer;

public final class RaasPaddingUtil {

    private RaasPaddingUtil() { }

    public static String leftPad(final String value, final int length) {
        if (value == null || value.isBlank()) {
            return "0".repeat(length);
        }
        final String numeric = numericOnly(value);
        if (numeric.length() >= length) {
            return numeric.substring(numeric.length() - length);
        }
        return "0".repeat(length - numeric.length()) + numeric;
    }

    public static String rightPad(final String value, final int length) {
        if (value == null || value.isBlank()) {
            return " ".repeat(length);
        }
        final String normalized = removerAcentos(value);
        if (normalized.length() >= length) {
            return normalized.substring(0, length);
        }
        return normalized + " ".repeat(length - normalized.length());
    }

    public static String leftPadZeros(final String value, final int length) {
        if (value == null || value.isBlank()) {
            return "0".repeat(length);
        }
        final String normalized = removerAcentos(value);
        if (normalized.length() >= length) {
            return normalized.substring(normalized.length() - length);
        }
        return "0".repeat(length - normalized.length()) + normalized;
    }

    public static String numericOnly(final String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\D", "");
    }

    public static String apenasDigitos(final String value) {
        return numericOnly(value);
    }

    public static String removerAcentos(final String value) {
        if (value == null) {
            return "";
        }
        final String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }
}
