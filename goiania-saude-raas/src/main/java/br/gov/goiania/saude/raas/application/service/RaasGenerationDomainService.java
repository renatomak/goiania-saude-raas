package br.gov.goiania.saude.raas.application.service;

public final class RaasGenerationDomainService {

    private RaasGenerationDomainService() { }

    private static final int DIVISOR_CONTROLE = 1111;

    public static long calcularCampoControle(
            final long subtotalAcoes,
            final long subtotalCadastros) {
        final long total = subtotalAcoes + subtotalCadastros;
        return DIVISOR_CONTROLE + (total % DIVISOR_CONTROLE);
    }

    public static String resolverCns(final String cns, final String cpf,
                                     final int tamanho) {
        if (isCpfValido(cpf)) {
            return RaasPaddingUtil.leftPadZeros("", tamanho);
        }
        if (cns != null && !cns.isBlank()) {
            return RaasPaddingUtil.rightPad(cns, tamanho);
        }
        return RaasPaddingUtil.leftPadZeros("", tamanho);
    }

    public static String resolverCpf(final String cpf, final String cns,
                                      final int tamanho) {
        if (isCpfValido(cpf)) {
            return RaasPaddingUtil.leftPadZeros(cpf, tamanho);
        }
        return RaasPaddingUtil.leftPadZeros("", tamanho);
    }

    public static boolean isCpfValido(final String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return false;
        }
        final String limpo = cpf.replaceAll("\\D", "");
        if (limpo.length() != 11 || limpo.matches("(\\d)\\1{10}")) {
            return false;
        }
        return validarDigitosCpf(limpo);
    }

    private static boolean validarDigitosCpf(final String cpf) {
        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * peso--;
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) {
            digito1 = 0;
        }
        if (digito1 != Integer.parseInt(cpf.substring(9, 10))) {
            return false;
        }
        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * peso--;
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) {
            digito2 = 0;
        }
        return digito2 == Integer.parseInt(cpf.substring(10, 11));
    }

    public static void validarTamanhoLinha(final String linha,
                                            final int tamanhoEsperado,
                                            final String tipoLinha) {
        if (linha.length() != tamanhoEsperado) {
            throw new IllegalStateException(
                    "Linha " + tipoLinha + " com tamanho " + linha.length()
                            + ", esperado " + tamanhoEsperado);
        }
    }
}
