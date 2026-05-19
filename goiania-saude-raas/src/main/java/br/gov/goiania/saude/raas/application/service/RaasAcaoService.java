package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import org.springframework.stereotype.Service;

@Service
public class RaasAcaoService {

    private static final String CODIGO_LINHA = "16";
    private static final String ORIGEM_INFORMACOES = "EXT";
    private static final String FILLER_4 = "    ";
    private static final int TAMANHO_LINHA = 110;

    private static final int TAM_UF = 2;
    private static final int TAM_COMPETENCIA = 6;
    private static final int TAM_CNES = 7;
    private static final int TAM_CNS = 15;
    private static final int TAM_DATA = 8;
    private static final int TAM_PROCEDIMENTO = 10;
    private static final int TAM_CBO = 6;
    private static final int TAM_CNS_PROFISSIONAL = 15;
    private static final int TAM_SERVICO = 3;
    private static final int TAM_CLASSIFICACAO = 3;
    private static final int TAM_QUANTIDADE = 6;
    private static final int TAM_LOCAL = 1;
    private static final int TAM_CPF = 11;

    public String gerarLinha16(final PacientePsicossocialDTO paciente,
                               final AcaoPsicossocialDTO acao) {
        final StringBuilder sb = new StringBuilder();
        sb.append(CODIGO_LINHA);
        sb.append(RaasPaddingUtil.leftPad(paciente.getUf(), TAM_UF));
        sb.append(RaasPaddingUtil.leftPad(paciente.getCompetencia(), TAM_COMPETENCIA));
        sb.append(RaasPaddingUtil.leftPad(paciente.getCnes(), TAM_CNES));
        sb.append(formatarCns(paciente));
        sb.append(RaasPaddingUtil.rightPad(paciente.getDataInicio(), TAM_DATA));
        sb.append(RaasPaddingUtil.leftPad(acao.getProcedimento(), TAM_PROCEDIMENTO));
        sb.append(RaasPaddingUtil.leftPad(acao.getCbo(), TAM_CBO));
        sb.append(RaasPaddingUtil.rightPad(acao.getCnsProfissional(), TAM_CNS_PROFISSIONAL));
        sb.append(RaasPaddingUtil.rightPad(acao.getDataExecucao(), TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(acao.getServico(), TAM_SERVICO));
        sb.append(RaasPaddingUtil.rightPad(acao.getClassificacao(), TAM_CLASSIFICACAO));
        sb.append(RaasPaddingUtil.leftPad(acao.getQuantidade().toString(), TAM_QUANTIDADE));
        sb.append(ORIGEM_INFORMACOES);
        sb.append(RaasPaddingUtil.rightPad(acao.getLocalRealizacao(), TAM_LOCAL));
        sb.append(formatarCpf(paciente));
        sb.append(FILLER_4);

        final String linha = sb.toString();
        if (linha.length() != TAMANHO_LINHA) {
            throw new IllegalStateException(
                    "Linha 16 com tamanho " + linha.length()
                            + ", esperado " + TAMANHO_LINHA);
        }
        return linha;
    }

    private String formatarCns(final PacientePsicossocialDTO paciente) {
        if (paciente.getCnsPaciente() != null
                && !paciente.getCnsPaciente().isBlank()) {
            return RaasPaddingUtil.rightPad(paciente.getCnsPaciente(), TAM_CNS);
        }
        return RaasPaddingUtil.leftPad("", TAM_CNS);
    }

    private String formatarCpf(final PacientePsicossocialDTO paciente) {
        if (paciente.getCpfPaciente() != null
                && !paciente.getCpfPaciente().isBlank()) {
            return RaasPaddingUtil.leftPad(paciente.getCpfPaciente(), TAM_CPF);
        }
        return RaasPaddingUtil.leftPad("", TAM_CPF);
    }
}
