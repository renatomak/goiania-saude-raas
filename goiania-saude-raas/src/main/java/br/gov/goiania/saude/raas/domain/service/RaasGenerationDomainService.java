package br.gov.goiania.saude.raas.domain.service;

import br.gov.goiania.saude.raas.application.dto.AcaoPsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.Header;
import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import br.gov.goiania.saude.raas.application.dto.RaasRemessaPsicossocialDTO;
import br.gov.goiania.saude.raas.application.service.RaasPaddingUtil;
import br.gov.goiania.saude.raas.domain.model.RaasLineFormat;

public class RaasGenerationDomainService {

    public String gerarArquivoRaas(final RaasRemessaPsicossocialDTO remessa) {
        final StringBuilder sb = new StringBuilder();
        final long campoControle = calcularCampoControle(remessa);

        sb.append(gerarLinhaHeader(remessa.getHeader(), campoControle));
        sb.append(RaasLineFormat.LINE_SEPARATOR);

        for (final PacientePsicossocialDTO paciente : remessa.getPacientes()) {
            sb.append(gerarLinhaPaciente(paciente));
            sb.append(RaasLineFormat.LINE_SEPARATOR);
            for (final AcaoPsicossocialDTO acao : paciente.getAcoes()) {
                sb.append(gerarLinhaAcao(paciente, acao));
                sb.append(RaasLineFormat.LINE_SEPARATOR);
            }
        }

        return sb.toString();
    }

    public String gerarLinhaHeader(final Header header, final long campoControle) {
        final StringBuilder sb = new StringBuilder();
        sb.append(RaasLineFormat.Header.CODIGO_LINHA);
        sb.append(RaasLineFormat.Header.IDENTIFICADOR_ARQUIVO);
        sb.append(leftPadZero(header.getCompetencia(), RaasLineFormat.Header.TAM_COMPETENCIA));
        sb.append(leftPadZero(String.valueOf(header.getQuantidadeFolhas()), RaasLineFormat.Header.TAM_QUANTIDADE_FOLHAS));
        sb.append(formatarCampoControle(campoControle));
        sb.append(rightPadSpace(header.getNomeResponsavel(), RaasLineFormat.Header.TAM_NOME_ORGAO_RESPONSAVEL));
        sb.append(rightPadSpace(header.getSiglaResponsavel(), RaasLineFormat.Header.TAM_SIGLA_ORGAO_RESPONSAVEL));
        sb.append(leftPadZero(apenasDigitos(header.getCnpjResponsavel()), RaasLineFormat.Header.TAM_CNPJ_ORGAO_RESPONSAVEL));
        sb.append(rightPadSpace(header.getNomeDestino(), RaasLineFormat.Header.TAM_NOME_ORGAO_DESTINO));
        sb.append(RaasLineFormat.Header.INDICADOR_DESTINO);
        sb.append(rightPadSpace(header.getDataGeracao(), RaasLineFormat.Header.TAM_DATA_GERACAO));
        sb.append(rightPadSpace(header.getVersaoSistema(), RaasLineFormat.Header.TAM_VERSAO_SISTEMA));
        sb.append(RaasLineFormat.Header.CAMPO_RESERVADO);
        sb.append(rightPadSpace(header.getVersaoBdsia(), RaasLineFormat.Header.TAM_VERSAO_BDSIA));
        sb.append(RaasLineFormat.Header.FINAL_DE_LINHA);

        final String linha = sb.toString();
        validarTamanhoLinha(linha, RaasLineFormat.Header.TAMANHO_LINHA, "Linha 01 (Header)");
        return linha;
    }

    public String gerarLinhaPaciente(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();

        sb.append(RaasLineFormat.Paciente.CODIGO_LINHA);
        adicionarParteIdentificacao(sb, p);
        adicionarParteEnderecoNascimento(sb, p);
        adicionarParteResponsavelContato(sb, p);
        adicionarParteAdministrativa(sb, p);
        adicionarParteFinal(sb, p);

        final String linha = sb.toString();
        validarTamanhoLinha(linha, RaasLineFormat.Paciente.TAMANHO_LINHA, "Linha 15 (Paciente)");
        return linha;
    }

    private void adicionarParteIdentificacao(final StringBuilder sb, final PacientePsicossocialDTO p) {
        sb.append(RaasPaddingUtil.leftPad(p.getUf(), RaasLineFormat.Paciente.TAM_UF));
        sb.append(RaasPaddingUtil.leftPad(p.getCompetencia(), RaasLineFormat.Paciente.TAM_COMPETENCIA));
        sb.append(RaasPaddingUtil.leftPad(p.getCnes(), RaasLineFormat.Paciente.TAM_CNES));
        sb.append(formatarCns(p));
        sb.append(RaasPaddingUtil.rightPad(p.getDataInicio(), RaasLineFormat.Paciente.TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(p.getDataFim(), RaasLineFormat.Paciente.TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(p.getNomePaciente(), RaasLineFormat.Paciente.TAM_NOME));
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroProntuario(), RaasLineFormat.Paciente.TAM_PRONTUARIO));
        sb.append(RaasPaddingUtil.rightPad(p.getNomeMae(), RaasLineFormat.Paciente.TAM_NOME_MAE));
    }

    private void adicionarParteEnderecoNascimento(final StringBuilder sb, final PacientePsicossocialDTO p) {
        sb.append(RaasPaddingUtil.rightPad(p.getLogradouro(), RaasLineFormat.Paciente.TAM_LOGRADOURO));
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroEndereco(), RaasLineFormat.Paciente.TAM_NUMERO_END));
        sb.append(RaasPaddingUtil.rightPad(p.getComplemento(), RaasLineFormat.Paciente.TAM_COMPLEMENTO));
        sb.append(RaasPaddingUtil.rightPad(p.getCep(), RaasLineFormat.Paciente.TAM_CEP));
        sb.append(RaasPaddingUtil.rightPad(p.getMunicipioIbge(), RaasLineFormat.Paciente.TAM_MUNICIPIO));
        sb.append(RaasPaddingUtil.rightPad(p.getDataNascimento(), RaasLineFormat.Paciente.TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(p.getSexo(), RaasLineFormat.Paciente.TAM_SEXO));
        sb.append(RaasPaddingUtil.rightPad(p.getRacaCor(), RaasLineFormat.Paciente.TAM_RACA));
    }

    private void adicionarParteResponsavelContato(final StringBuilder sb, final PacientePsicossocialDTO p) {
        sb.append(RaasPaddingUtil.rightPad(p.getNomeResponsavel(), RaasLineFormat.Paciente.TAM_RESPONSAVEL));
        sb.append(RaasLineFormat.Paciente.NACIONALIDADE_BRASILEIRA);
        sb.append(RaasPaddingUtil.rightPad(p.getEtnia(), RaasLineFormat.Paciente.TAM_ETNIA));
        sb.append(RaasPaddingUtil.rightPad(p.getTelefone(), RaasLineFormat.Paciente.TAM_TELEFONE));
        sb.append(RaasPaddingUtil.rightPad(p.getCelular(), RaasLineFormat.Paciente.TAM_CELULAR));
        sb.append(RaasPaddingUtil.rightPad(p.getMotivoSaida(), RaasLineFormat.Paciente.TAM_MOTIVO_SAIDA));
    }

    private void adicionarParteAdministrativa(final StringBuilder sb, final PacientePsicossocialDTO p) {
        sb.append("        ");
        sb.append(RaasPaddingUtil.rightPad(p.getCidPrincipal(), RaasLineFormat.Paciente.TAM_CID));
        sb.append("    ".repeat(4));
        sb.append("  ");
        sb.append(RaasPaddingUtil.rightPad(p.getOrigemPaciente(), RaasLineFormat.Paciente.TAM_ORIGEM));
        sb.append(RaasPaddingUtil.rightPad(p.getCoberturaEsf(), RaasLineFormat.Paciente.TAM_COBERTURA_ESF));
        sb.append(RaasPaddingUtil.rightPad(p.getCnesEsf(), RaasLineFormat.Paciente.TAM_CNES_ESF));
        sb.append(RaasPaddingUtil.leftPad(String.valueOf(p.getAcoes().size()), RaasLineFormat.Paciente.TAM_TOTAL_ACOES));
        sb.append(RaasPaddingUtil.rightPad(p.getDestinoPaciente(), RaasLineFormat.Paciente.TAM_DESTINO));
        sb.append(RaasLineFormat.Paciente.ORIGEM_INFORMACOES);
        sb.append(RaasPaddingUtil.rightPad(p.getSituacaoRua(), RaasLineFormat.Paciente.TAM_SITUACAO_RUA));
        sb.append(RaasPaddingUtil.rightPad(p.getUsuarioDrogas(), RaasLineFormat.Paciente.TAM_USUARIO_DROGA));
        sb.append(formatarTipoDroga(p));
    }

    private void adicionarParteFinal(final StringBuilder sb, final PacientePsicossocialDTO p) {
        sb.append("             ");
        sb.append(RaasPaddingUtil.rightPad(p.getDescricaoBairro(), RaasLineFormat.Paciente.TAM_BAIRRO));
        sb.append(RaasPaddingUtil.rightPad(p.getTipoLogradouro(), RaasLineFormat.Paciente.TAM_TIPO_LOGRADOURO));
        sb.append(RaasPaddingUtil.rightPad(p.getEmailPaciente(), RaasLineFormat.Paciente.TAM_EMAIL));
        sb.append(formatarCpf(p));
        sb.append(RaasLineFormat.Paciente.FILLER_4);
    }

    public String gerarLinhaAcao(final PacientePsicossocialDTO paciente, final AcaoPsicossocialDTO acao) {
        final StringBuilder sb = new StringBuilder();

        sb.append(RaasLineFormat.Acao.CODIGO_LINHA);
        sb.append(RaasPaddingUtil.leftPad(paciente.getUf(), RaasLineFormat.Acao.TAM_UF));
        sb.append(RaasPaddingUtil.leftPad(paciente.getCompetencia(), RaasLineFormat.Acao.TAM_COMPETENCIA));
        sb.append(RaasPaddingUtil.leftPad(paciente.getCnes(), RaasLineFormat.Acao.TAM_CNES));
        sb.append(formatarCnsAcao(paciente));
        sb.append(RaasPaddingUtil.rightPad(paciente.getDataInicio(), RaasLineFormat.Acao.TAM_DATA));
        sb.append(RaasPaddingUtil.leftPad(acao.getProcedimento(), RaasLineFormat.Acao.TAM_PROCEDIMENTO));
        sb.append(RaasPaddingUtil.leftPad(acao.getCbo(), RaasLineFormat.Acao.TAM_CBO));
        sb.append(RaasPaddingUtil.rightPad(acao.getCnsProfissional(), RaasLineFormat.Acao.TAM_CNS_PROFISSIONAL));
        sb.append(RaasPaddingUtil.rightPad(acao.getDataExecucao(), RaasLineFormat.Acao.TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(acao.getServico(), RaasLineFormat.Acao.TAM_SERVICO));
        sb.append(RaasPaddingUtil.rightPad(acao.getClassificacao(), RaasLineFormat.Acao.TAM_CLASSIFICACAO));
        sb.append(RaasPaddingUtil.leftPad(acao.getQuantidade().toString(), RaasLineFormat.Acao.TAM_QUANTIDADE));
        sb.append(RaasLineFormat.Acao.ORIGEM_INFORMACOES);
        sb.append(RaasPaddingUtil.rightPad(acao.getLocalRealizacao(), RaasLineFormat.Acao.TAM_LOCAL));
        sb.append(formatarCpfAcao(paciente));
        sb.append(RaasLineFormat.Acao.FILLER_4);

        final String linha = sb.toString();
        validarTamanhoLinha(linha, RaasLineFormat.Acao.TAMANHO_LINHA, "Linha 16 (Ação)");
        return linha;
    }

    public long calcularCampoControle(final RaasRemessaPsicossocialDTO remessa) {
        long soma = 0;
        for (final PacientePsicossocialDTO p : remessa.getPacientes()) {
            soma += parseLongOrZero(p.getCnes());
            soma += parseLongOrZero(p.getCnsPaciente());
            for (final AcaoPsicossocialDTO a : p.getAcoes()) {
                soma += parseLongOrZero(a.getProcedimento());
                soma += a.getQuantidade() != null ? a.getQuantidade() : 0;
            }
        }
        return RaasLineFormat.DIVISOR_CONTROLE + (soma % RaasLineFormat.DIVISOR_CONTROLE);
    }

    private String formatarCns(final PacientePsicossocialDTO p) {
        if (p.getCnsPaciente() != null && !p.getCnsPaciente().isBlank()) {
            return RaasPaddingUtil.rightPad(p.getCnsPaciente(), RaasLineFormat.Paciente.TAM_CNS);
        }
        return "000000000000000";
    }

    private String formatarCnsAcao(final PacientePsicossocialDTO paciente) {
        if (paciente.getCnsPaciente() != null && !paciente.getCnsPaciente().isBlank()) {
            return RaasPaddingUtil.rightPad(paciente.getCnsPaciente(), RaasLineFormat.Acao.TAM_CNS);
        }
        return RaasPaddingUtil.leftPad("", RaasLineFormat.Acao.TAM_CNS);
    }

    private String formatarCpf(final PacientePsicossocialDTO p) {
        if (p.getCpfPaciente() != null && !p.getCpfPaciente().isBlank()) {
            return RaasPaddingUtil.leftPad(p.getCpfPaciente(), RaasLineFormat.Paciente.TAM_CPF);
        }
        return "00000000000";
    }

    private String formatarCpfAcao(final PacientePsicossocialDTO paciente) {
        if (paciente.getCpfPaciente() != null && !paciente.getCpfPaciente().isBlank()) {
            return RaasPaddingUtil.leftPad(paciente.getCpfPaciente(), RaasLineFormat.Acao.TAM_CPF);
        }
        return RaasPaddingUtil.leftPad("", RaasLineFormat.Acao.TAM_CPF);
    }

    private String formatarTipoDroga(final PacientePsicossocialDTO p) {
        if (!"S".equalsIgnoreCase(p.getUsuarioDrogas())) {
            return " ".repeat(RaasLineFormat.Paciente.TAM_TIPO_DROGA);
        }
        return RaasPaddingUtil.rightPad(p.getTipoDrogaAlcool(), 1)
                + RaasPaddingUtil.rightPad(p.getTipoDrogaCrack(), 1)
                + RaasPaddingUtil.rightPad(p.getTipoDrogaOutros(), 1);
    }

    private String formatarCampoControle(final long campoControle) {
        return String.format("%0" + RaasLineFormat.Header.TAM_CAMPO_CONTROLE + "d", campoControle);
    }

    private long parseLongOrZero(final String value) {
        if (value == null) {
            return 0;
        }
        try {
            return Long.parseLong(value.replaceAll("\\D", ""));
        } catch (final NumberFormatException e) {
            return 0;
        }
    }

    private void validarTamanhoLinha(final String linha, final int tamanhoEsperado, final String nomeLinha) {
        if (linha.length() != tamanhoEsperado) {
            throw new IllegalStateException(
                    "%s gerada com tamanho incorreto: %d (esperado: %d)"
                            .formatted(nomeLinha, linha.length(), tamanhoEsperado));
        }
    }


    private String leftPadZero(final String value, final int length) {
        if (value == null || value.isBlank()) {
            return "0".repeat(length);
        }
        final String numeric = apenasDigitos(value);
        if (numeric.length() >= length) {
            return numeric.substring(numeric.length() - length);
        }
        return "0".repeat(length - numeric.length()) + numeric;
    }

    private String rightPadSpace(final String value, final int length) {
        if (value == null) {
            return " ".repeat(length);
        }
        if (value.length() >= length) {
            return value.substring(0, length);
        }
        return value + " ".repeat(length - value.length());
    }

    private String apenasDigitos(final String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\D", "");
    }
}


