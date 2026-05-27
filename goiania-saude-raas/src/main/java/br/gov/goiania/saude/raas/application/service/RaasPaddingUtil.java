package br.gov.goiania.saude.raas.application.service;

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
        if (value.length() >= length) {
            return value.substring(0, length);
        }
        return value + " ".repeat(length - value.length());
    }

    public static String leftPadZeros(final String value, final int length) {
        if (value == null || value.isBlank()) {
            return "0".repeat(length);
        }
        if (value.length() >= length) {
            return value.substring(value.length() - length);
        }
        return "0".repeat(length - value.length()) + value;
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
}

