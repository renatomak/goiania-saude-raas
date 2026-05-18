package br.gov.goiania.saude.raas.application.util;

public final class StringUtil {
    private StringUtil() { }

    public static String leftPadZero(final String valor, final int tamanho) {
        final String soDigitos = apenasDigitos(valor);
        if (soDigitos.length() >= tamanho) {
            return soDigitos.substring(soDigitos.length() - tamanho);
        }
        return "0".repeat(tamanho - soDigitos.length()) + soDigitos;
    }

    public static String rightPadSpace(final String valor, final int tamanho) {
        if (valor == null) {
            return " ".repeat(tamanho);
        }
        if (valor.length() >= tamanho) {
            return valor.substring(0, tamanho);
        }
        return valor + " ".repeat(tamanho - valor.length());
    }

    public static String apenasDigitos(final String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replaceAll("\\D", "");
    }
}
