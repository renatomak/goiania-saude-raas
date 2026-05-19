package br.gov.goiania.saude.raas.application.service;

import br.gov.goiania.saude.raas.application.dto.PacientePsicossocialDTO;
import org.springframework.stereotype.Service;

@Service
public class RaasPacienteService {

    private static final String CODIGO_LINHA = "15";
    private static final String NACIONALIDADE_PADRAO = "010";
    private static final String ORIGEM_INFORMACOES = "EXT";
    private static final String FILLER_4 = "    ";
    private static final String FILLER_2 = "  ";
    private static final String FILLER_8 = "        ";
    private static final String FILLER_13 = "             ";
    private static final int TAMANHO_LINHA = 406;

    private static final int TAM_UF = 2;
    private static final int TAM_COMPETENCIA = 6;
    private static final int TAM_CNES = 7;
    private static final int TAM_CNS = 15;
    private static final int TAM_DATA = 8;
    private static final int TAM_NOME = 30;
    private static final int TAM_PRONTUARIO = 10;
    private static final int TAM_MAE = 30;
    private static final int TAM_LOGRADOURO = 30;
    private static final int TAM_NUMERO = 5;
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
    private static final int TAM_USUARIO_DROGAS = 1;
    private static final int TAM_TIPO_DROGA = 1;
    private static final int TAM_BAIRRO = 30;
    private static final int TAM_TIPO_LOGRADOURO = 3;
    private static final int TAM_EMAIL = 40;
    private static final int TAM_CPF = 11;

    public String gerarLinha15(final PacientePsicossocialDTO paciente) {
        final StringBuilder sb = new StringBuilder();
        sb.append(gerarParte1(paciente));
        sb.append(gerarParte2(paciente));
        sb.append(gerarParte3(paciente));
        sb.append(gerarParte4(paciente));

        final String linha = sb.toString();
        if (linha.length() != TAMANHO_LINHA) {
            throw new IllegalStateException(
                    "Linha 15 com tamanho " + linha.length()
                            + ", esperado " + TAMANHO_LINHA);
        }
        return linha;
    }

    private String gerarParte1(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append(CODIGO_LINHA);
        sb.append(RaasPaddingUtil.leftPad(p.getUf(), TAM_UF));
        sb.append(RaasPaddingUtil.leftPad(p.getCompetencia(), TAM_COMPETENCIA));
        sb.append(RaasPaddingUtil.leftPad(p.getCnes(), TAM_CNES));
        sb.append(formatarCns(p));
        sb.append(RaasPaddingUtil.rightPad(p.getDataInicio(), TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(p.getDataFim(), TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(p.getNomePaciente(), TAM_NOME));
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroProntuario(), TAM_PRONTUARIO));
        sb.append(RaasPaddingUtil.rightPad(p.getNomeMae(), TAM_MAE));
        sb.append(RaasPaddingUtil.rightPad(p.getLogradouro(), TAM_LOGRADOURO));
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroEndereco(), TAM_NUMERO));
        sb.append(RaasPaddingUtil.rightPad(p.getComplemento(), TAM_COMPLEMENTO));
        sb.append(RaasPaddingUtil.rightPad(p.getCep(), TAM_CEP));
        return sb.toString();
    }

    private String gerarParte2(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append(formatarMunicipio(p.getMunicipioIbge()));
        sb.append(RaasPaddingUtil.rightPad(p.getDataNascimento(), TAM_DATA));
        sb.append(RaasPaddingUtil.rightPad(p.getSexo(), TAM_SEXO));
        sb.append(RaasPaddingUtil.rightPad(p.getRacaCor(), TAM_RACA));
        sb.append(RaasPaddingUtil.rightPad(p.getNomeResponsavel(), TAM_RESPONSAVEL));
        sb.append(NACIONALIDADE_PADRAO);
        sb.append(RaasPaddingUtil.rightPad(p.getEtnia(), TAM_ETNIA));
        sb.append(RaasPaddingUtil.rightPad(p.getTelefone(), TAM_TELEFONE));
        sb.append(RaasPaddingUtil.rightPad(p.getCelular(), TAM_CELULAR));
        sb.append(RaasPaddingUtil.rightPad(p.getMotivoSaida(), TAM_MOTIVO_SAIDA));
        sb.append(FILLER_8);
        sb.append(RaasPaddingUtil.rightPad(p.getCidPrincipal(), TAM_CID));
        return sb.toString();
    }

    private String gerarParte3(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append(FILLER_4);
        sb.append(FILLER_4);
        sb.append(FILLER_4);
        sb.append(FILLER_4);
        sb.append(FILLER_2);
        sb.append(RaasPaddingUtil.rightPad(p.getOrigemPaciente(), TAM_ORIGEM));
        sb.append(RaasPaddingUtil.rightPad(p.getCoberturaEsf(), TAM_COBERTURA_ESF));
        sb.append(RaasPaddingUtil.rightPad(p.getCnesEsf(), TAM_CNES_ESF));
        sb.append(RaasPaddingUtil.leftPad(String.valueOf(p.getAcoes().size()), TAM_TOTAL_ACOES));
        sb.append(RaasPaddingUtil.rightPad(p.getDestinoPaciente(), TAM_DESTINO));
        sb.append(ORIGEM_INFORMACOES);
        sb.append(RaasPaddingUtil.rightPad(p.getSituacaoRua(), TAM_SITUACAO_RUA));
        sb.append(RaasPaddingUtil.rightPad(p.getUsuarioDrogas(), TAM_USUARIO_DROGAS));
        sb.append(RaasPaddingUtil.rightPad(p.getTipoDrogaAlcool(), TAM_TIPO_DROGA));
        sb.append(RaasPaddingUtil.rightPad(p.getTipoDrogaCrack(), TAM_TIPO_DROGA));
        sb.append(RaasPaddingUtil.rightPad(p.getTipoDrogaOutros(), TAM_TIPO_DROGA));
        return sb.toString();
    }

    private String gerarParte4(final PacientePsicossocialDTO p) {
        final StringBuilder sb = new StringBuilder();
        sb.append(FILLER_13);
        sb.append(RaasPaddingUtil.rightPad(p.getDescricaoBairro(), TAM_BAIRRO));
        sb.append(RaasPaddingUtil.rightPad(p.getTipoLogradouro(), TAM_TIPO_LOGRADOURO));
        sb.append(RaasPaddingUtil.rightPad(p.getEmailPaciente(), TAM_EMAIL));
        sb.append(formatarCpf(p));
        sb.append(FILLER_4);
        return sb.toString();
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

    private String formatarMunicipio(final String municipio) {
        if (municipio == null || municipio.isBlank()) {
            return RaasPaddingUtil.rightPad("", TAM_MUNICIPIO);
        }
        if (municipio.length() <= TAM_MUNICIPIO) {
            return RaasPaddingUtil.rightPad(municipio, TAM_MUNICIPIO);
        }
        return municipio.substring(0, TAM_MUNICIPIO);
    }
}
