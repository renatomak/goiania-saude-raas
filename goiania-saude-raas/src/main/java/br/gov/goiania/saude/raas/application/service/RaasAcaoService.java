package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import org.springframework.stereotype.Service;

@Service
public class RaasAcaoService {

    private static final int TAMANHO_LINHA = 110;

    private static final String CODIGO_LINHA = "16";
    private static final String ORIGEM_INFORMACOES = "EXT";
    private static final String FILLER_4 = "    ";

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
        final StringBuilder sb = new StringBuilder(TAMANHO_LINHA);
        sb.append(CODIGO_LINHA);
        sb.append(RaasPaddingUtil.leftPadZeros(paciente.getUf(), TAM_UF));
        sb.append(RaasPaddingUtil.leftPadZeros(paciente.getCompetencia(), TAM_COMPETENCIA));
        sb.append(RaasPaddingUtil.leftPadZeros(paciente.getCnes(), TAM_CNES));
        sb.append(RaasGenerationDomainService.resolverCns(
                paciente.getCnsPaciente(), paciente.getCpfPaciente(), TAM_CNS));
        sb.append(RaasPaddingUtil.rightPad(paciente.getDataInicio(), TAM_DATA));
        sb.append(RaasPaddingUtil.leftPadZeros(acao.getProcedimento(), TAM_PROCEDIMENTO));
        sb.append(RaasPaddingUtil.leftPadZeros(acao.getCbo(), TAM_CBO));
        sb.append(RaasPaddingUtil.rightPad(acao.getCnsProfissional(), TAM_CNS_PROFISSIONAL));
        sb.append(RaasPaddingUtil.rightPad(acao.getDataExecucao(), TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(acao.getServico(), TAM_SERVICO));
        sb.append(RaasPaddingUtil.rightPad(acao.getClassificacao(), TAM_CLASSIFICACAO));
        sb.append(RaasPaddingUtil.leftPadZeros(acao.getQuantidade().toString(), TAM_QUANTIDADE));
        sb.append(ORIGEM_INFORMACOES);
        sb.append(RaasPaddingUtil.rightPad(acao.getLocalRealizacao(), TAM_LOCAL));
        sb.append(RaasGenerationDomainService.resolverCpf(
                paciente.getCpfPaciente(), paciente.getCnsPaciente(), TAM_CPF));
        sb.append(FILLER_4);

        final String linha = sb.toString();
        RaasGenerationDomainService.validarTamanhoLinha(
                linha, TAMANHO_LINHA, "16");
        return linha;
    }
}
