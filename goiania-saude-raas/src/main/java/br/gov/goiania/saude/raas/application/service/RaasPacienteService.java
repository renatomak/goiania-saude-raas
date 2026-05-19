package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import org.springframework.stereotype.Service;

@Service
public class RaasPacienteService {

    private static final int TAMANHO_LINHA_15 = 406;

    private static final String CODIGO_LINHA_15 = "15";
    private static final String NACIONALIDADE_BRASILEIRA = "010";
    private static final String ORIGEM_INFORMACOES = "EXT";
    private static final String FILLER_4 = "    ";

    private static final int TAM_UF = 2;
    private static final int TAM_COMPETENCIA = 6;
    private static final int TAM_CNES = 7;
    private static final int TAM_CNS = 15;
    private static final int TAM_DATA = 8;
    private static final int TAM_NOME = 30;
    private static final int TAM_PRONTUARIO = 10;
    private static final int TAM_NOME_MAE = 30;
    private static final int TAM_LOGRADOURO = 30;
    private static final int TAM_NUMERO_END = 5;
    private static final int TAM_COMPLEMENTO = 10;
    private static final int TAM_CEP = 8;
    private static final int TAM_MUNICIPIO = 7;
    private static final int TAM_SEXO = 1;
    private static final int TAM_RACA = 2;
    private static final int TAM_RESPONSAVEL = 30;
    private static final int TAM_ETNIA = 4;
    private static final int TAM_TELEFONE = 11;
    private static final int TAM_CELULAR = 11;
    private static final int TAM_MOTIVO_SAIDA = 2;
    private static final int TAM_CID = 4;
    private static final int TAM_ORIGEM = 2;
    private static final int TAM_COBERTURA_ESF = 1;
    private static final int TAM_CNES_ESF = 7;
    private static final int TAM_TOTAL_ACOES = 5;
    private static final int TAM_DESTINO = 2;
    private static final int TAM_SITUACAO_RUA = 1;
    private static final int TAM_USUARIO_DROGA = 1;
    private static final int TAM_TIPO_DROGA = 3;
    private static final int TAM_BAIRRO = 30;
    private static final int TAM_TIPO_LOGRADOURO = 3;
    private static final int TAM_EMAIL = 40;
    private static final int TAM_CPF = 11;
    private static final int TAM_FILLER_FINAL = 4;

    public String gerarLinha15(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder(TAMANHO_LINHA_15);
        sb.append(gerarParteIdentificacao(p));
        sb.append(gerarParteEnderecoNascimento(p));
        sb.append(gerarParteResponsavelContato(p));
        sb.append(gerarParteAdministrativa(p));
        sb.append(gerarParteFinal(p));

        final String linha = sb.toString();
        RaasGenerationDomainService.validarTamanhoLinha(
                linha, TAMANHO_LINHA_15, "15");
        return linha;
    }

    private String gerarParteIdentificacao(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append(CODIGO_LINHA_15);
        sb.append(RaasPaddingUtil.leftPad(p.getUf(), TAM_UF));
        sb.append(RaasPaddingUtil.leftPad(p.getCompetencia(), TAM_COMPETENCIA));
        sb.append(RaasPaddingUtil.leftPad(p.getCnes(), TAM_CNES));
        sb.append(RaasGenerationDomainService.resolverCns(
                p.getCnsPaciente(), p.getCpfPaciente(), TAM_CNS));
        sb.append(RaasPaddingUtil.rightPad(p.getDataInicio(), TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(p.getDataFim(), TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(p.getNomePaciente(), TAM_NOME));
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroProntuario(), TAM_PRONTUARIO));
        sb.append(RaasPaddingUtil.rightPad(p.getNomeMae(), TAM_NOME_MAE));
        return sb.toString();
    }

    private String gerarParteEnderecoNascimento(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append(RaasPaddingUtil.rightPad(p.getLogradouro(), TAM_LOGRADOURO));
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroEndereco(), TAM_NUMERO_END));
        sb.append(RaasPaddingUtil.rightPad(p.getComplemento(), TAM_COMPLEMENTO));
        sb.append(RaasPaddingUtil.rightPad(p.getCep(), TAM_CEP));
        sb.append(RaasPaddingUtil.rightPad(p.getMunicipioIbge(), TAM_MUNICIPIO));
        sb.append(RaasPaddingUtil.rightPad(p.getDataNascimento(), TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(p.getSexo(), TAM_SEXO));
        sb.append(RaasPaddingUtil.rightPad(p.getRacaCor(), TAM_RACA));
        return sb.toString();
    }

    private String gerarParteResponsavelContato(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append(RaasPaddingUtil.rightPad(p.getNomeResponsavel(), TAM_RESPONSAVEL));
        sb.append(NACIONALIDADE_BRASILEIRA);
        sb.append(RaasPaddingUtil.rightPad(p.getEtnia(), TAM_ETNIA));
        sb.append(RaasPaddingUtil.rightPad(p.getTelefone(), TAM_TELEFONE));
        sb.append(RaasPaddingUtil.rightPad(p.getCelular(), TAM_CELULAR));
        sb.append(RaasPaddingUtil.rightPad(p.getMotivoSaida(), TAM_MOTIVO_SAIDA));
        return sb.toString();
    }

    private String gerarParteAdministrativa(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append("        ");
        sb.append(RaasPaddingUtil.rightPad(p.getCidPrincipal(), TAM_CID));
        sb.append("    ".repeat(4));
        sb.append("  ");
        sb.append(RaasPaddingUtil.rightPad(p.getOrigemPaciente(), TAM_ORIGEM));
        sb.append(RaasPaddingUtil.rightPad(p.getCoberturaEsf(), TAM_COBERTURA_ESF));
        sb.append(RaasPaddingUtil.rightPad(p.getCnesEsf(), TAM_CNES_ESF));
        sb.append(RaasPaddingUtil.leftPad(
                String.valueOf(p.getAcoes().size()), TAM_TOTAL_ACOES));
        sb.append(RaasPaddingUtil.rightPad(p.getDestinoPaciente(), TAM_DESTINO));
        sb.append(ORIGEM_INFORMACOES);
        sb.append(RaasPaddingUtil.rightPad(p.getSituacaoRua(), TAM_SITUACAO_RUA));
        sb.append(RaasPaddingUtil.rightPad(p.getUsuarioDrogas(), TAM_USUARIO_DROGA));
        sb.append(formatarTipoDroga(p));
        return sb.toString();
    }

    private String gerarParteFinal(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append("             ");
        sb.append(RaasPaddingUtil.rightPad(p.getDescricaoBairro(), TAM_BAIRRO));
        sb.append(RaasPaddingUtil.rightPad(p.getTipoLogradouro(), TAM_TIPO_LOGRADOURO));
        sb.append(RaasPaddingUtil.rightPad(p.getEmailPaciente(), TAM_EMAIL));
        sb.append(RaasGenerationDomainService.resolverCpf(
                p.getCpfPaciente(), p.getCnsPaciente(), TAM_CPF));
        sb.append(FILLER_4);
        return sb.toString();
    }

    private String formatarTipoDroga(final PacientePsicossocialDTO p) {
        if (!"S".equalsIgnoreCase(p.getUsuarioDrogas())) {
            return " ".repeat(TAM_TIPO_DROGA);
        }
        return RaasPaddingUtil.rightPad(p.getTipoDrogaAlcool(), 1)
                + RaasPaddingUtil.rightPad(p.getTipoDrogaCrack(), 1)
                + RaasPaddingUtil.rightPad(p.getTipoDrogaOutros(), 1);
    }
}
