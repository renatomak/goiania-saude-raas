package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.HeaderDTO;
import br.gov.goiania.saude.raas.application.util.StringUtil;
import org.springframework.stereotype.Service;

@Service
public class RaasHeaderService {

    private static final String CODIGO_LINHA          = "01";
    private static final String IDENTIFICADOR_ARQUIVO = "#RAS#";
    private static final String INDICADOR_DESTINO     = "M";
    private static final String CAMPO_RESERVADO       = "          ";
    private static final String FINAL_DE_LINHA        = "               ";

    private static final int TAM_COMPETENCIA              = 6;
    private static final int TAM_QUANTIDADE_FOLHAS        = 6;
    private static final int TAM_CAMPO_CONTROLE           = 4;
    private static final int TAM_NOME_ORGAO_RESPONSAVEL   = 30;
    private static final int TAM_SIGLA_ORGAO_RESPONSAVEL  = 6;
    private static final int TAM_CNPJ_ORGAO_RESPONSAVEL   = 14;
    private static final int TAM_NOME_ORGAO_DESTINO       = 40;
    private static final int TAM_DATA_GERACAO             = 8;
    private static final int TAM_VERSAO_SISTEMA           = 5;
    private static final int TAM_VERSAO_BDSIA             = 7;
    private static final int TAM_LINHA                    = 159;

    public String gerarHeaderLinha01(final HeaderDTO header, final long campoControle) {
        final String linha = montarCampos(header, campoControle);
        validarTamanhoLinha(linha);
        return linha;
    }

    private String montarCampos(final HeaderDTO header, final long campoControle) {
        return new StringBuilder()
                .append(CODIGO_LINHA)
                .append(IDENTIFICADOR_ARQUIVO)
                .append(StringUtil.leftPadZero(header.getCompetencia(), TAM_COMPETENCIA))
                .append(StringUtil.leftPadZero(String.valueOf(header.getQuantidadeFolhas()), TAM_QUANTIDADE_FOLHAS))
                .append(formatarCampoControle(campoControle))
                .append(StringUtil.rightPadSpace(header.getNomeResponsavel(), TAM_NOME_ORGAO_RESPONSAVEL))
                .append(StringUtil.rightPadSpace(header.getSiglaResponsavel(), TAM_SIGLA_ORGAO_RESPONSAVEL))
                .append(StringUtil.leftPadZero(StringUtil.apenasDigitos(header.getCnpjResponsavel()), TAM_CNPJ_ORGAO_RESPONSAVEL))
                .append(StringUtil.rightPadSpace(header.getNomeDestino(), TAM_NOME_ORGAO_DESTINO))
                .append(INDICADOR_DESTINO)
                .append(StringUtil.rightPadSpace(header.getDataGeracao(), TAM_DATA_GERACAO))
                .append(StringUtil.rightPadSpace(header.getVersaoSistema(), TAM_VERSAO_SISTEMA))
                .append(CAMPO_RESERVADO)
                .append(StringUtil.rightPadSpace(header.getVersaoBdsia(), TAM_VERSAO_BDSIA))
                .append(FINAL_DE_LINHA)
                .toString();
    }

    private void validarTamanhoLinha(final String linha) {
        if (linha.length() != TAM_LINHA) {
            throw new IllegalStateException(
                    "Linha 01 com tamanho incorreto: esperado %d, obtido %d"
                            .formatted(TAM_LINHA, linha.length()));
        }
    }

    private String formatarCampoControle(final long campoControle) {
        return String.format("%0" + TAM_CAMPO_CONTROLE + "d", campoControle);
    }
}
