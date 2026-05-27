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

    public static String resolverCns(final String cns,
                                     final int tamanho) {
        if (cns != null && !cns.isBlank()) {
            return RaasPaddingUtil.rightPad(cns, tamanho);
        }
        return RaasPaddingUtil.leftPad("", tamanho);
    }

    public static String resolverCpf(final String cpf, final String cns,
                                      final int tamanho) {
        if (cpf != null && !cpf.isBlank()
                && (cns == null || cns.isBlank())) {
            return RaasPaddingUtil.leftPadZeros(cpf, tamanho);
        }
        return RaasPaddingUtil.leftPadZeros("", tamanho);
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
