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
    private static final String MOTIVO_PERMANENCIA_PADRAO = "00";

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
    private static final int TAM_DATA_OBITO_ALTA = 8;
    private static final int TAM_CID = 4;
    private static final int TAM_ORIGEM = 2;
    private static final int TAM_CARATER = 2;
    private static final int TAM_ORIGEM_PACIENTE = 2;
    private static final int TAM_COBERTURA_ESF = 1;
    private static final int TAM_CNES_ESF = 7;
    private static final int TAM_TOTAL_ACOES = 5;
    private static final int TAM_DESTINO = 2;
    private static final int TAM_SITUACAO_RUA = 1;
    private static final int TAM_USUARIO_DROGA = 1;
    private static final int TAM_TIPO_DROGA = 3;
    private static final int TAM_AUTORIZACAO = 13;
    private static final int TAM_BAIRRO = 30;
    private static final int TAM_TIPO_LOGRADOURO = 3;
    private static final int TAM_EMAIL = 40;
    private static final int TAM_CPF = 11;
    private static final int TAM_FILLER_FINAL = 4;

    public String gerarLinha15(PacientePsicossocialDTO p) {
        StringBuilder sb = new StringBuilder(TAMANHO_LINHA_15);

        sb.append(gerarParteIdentificacao(p));
        sb.append(gerarParteEnderecoNascimento(p));
        sb.append(gerarParteResponsavelContato(p));
        sb.append(gerarParteAdministrativa(p));
        sb.append(gerarParteFinal(p));

        String linha = sb.toString();

        if (linha.length() != TAMANHO_LINHA_15) {
            throw new IllegalStateException(
                    "Linha 15 gerada com tamanho incorreto: " + linha.length() + " (esperado: " + TAMANHO_LINHA_15 + ")");
        }

        return linha;
    }

    private String gerarParteIdentificacao(PacientePsicossocialDTO p) {
        StringBuilder sb = new StringBuilder();
        sb.append(CODIGO_LINHA_15);                                           // 001-002
        sb.append(RaasPaddingUtil.leftPad(p.getUf(), TAM_UF));               // 003-004
        sb.append(RaasPaddingUtil.leftPad(p.getCompetencia(), TAM_COMPETENCIA)); // 005-010
        sb.append(RaasPaddingUtil.leftPad(p.getCnes(), TAM_CNES));           // 011-017
        sb.append(formatarCns(p));                                             // 018-032
        sb.append(RaasPaddingUtil.rightPad(p.getDataInicio(), TAM_DATA));     // 033-040
        sb.append(RaasPaddingUtil.rightPad(p.getDataFim(), TAM_DATA));        // 041-048
        sb.append(RaasPaddingUtil.rightPad(p.getNomePaciente(), TAM_NOME));   // 049-078
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroProntuario(), TAM_PRONTUARIO)); // 079-088
        sb.append(RaasPaddingUtil.rightPad(p.getNomeMae(), TAM_NOME_MAE));    // 089-118
        return sb.toString();
    }

    private String gerarParteEnderecoNascimento(PacientePsicossocialDTO p) {
        StringBuilder sb = new StringBuilder();
        sb.append(RaasPaddingUtil.rightPad(p.getLogradouro(), TAM_LOGRADOURO));     // 119-148
        sb.append(RaasPaddingUtil.rightPad(p.getNumeroEndereco(), TAM_NUMERO_END)); // 149-153
        sb.append(RaasPaddingUtil.rightPad(p.getComplemento(), TAM_COMPLEMENTO));   // 154-163
        sb.append(RaasPaddingUtil.rightPad(p.getCep(), TAM_CEP));                   // 164-171
        sb.append(RaasPaddingUtil.rightPad(p.getMunicipioIbge(), TAM_MUNICIPIO));   // 172-178
        sb.append(RaasPaddingUtil.rightPad(p.getDataNascimento(), TAM_DATA));       // 179-186
        sb.append(RaasPaddingUtil.rightPad(p.getSexo(), TAM_SEXO));                 // 187
        sb.append(RaasPaddingUtil.rightPad(p.getRacaCor(), TAM_RACA));              // 188-189
        return sb.toString();
    }

    private String gerarParteResponsavelContato(PacientePsicossocialDTO p) {
        StringBuilder sb = new StringBuilder();
        sb.append(RaasPaddingUtil.rightPad(p.getNomeResponsavel(), TAM_RESPONSAVEL)); // 190-219
        sb.append(NACIONALIDADE_BRASILEIRA);                                         // 220-222
        sb.append(RaasPaddingUtil.rightPad(p.getEtnia(), TAM_ETNIA));                // 223-226
        sb.append(RaasPaddingUtil.rightPad(p.getTelefone(), TAM_TELEFONE));          // 227-237
        sb.append(RaasPaddingUtil.rightPad(p.getCelular(), TAM_CELULAR));            // 238-248
        sb.append(RaasPaddingUtil.rightPad(p.getMotivoSaida(), TAM_MOTIVO_SAIDA));   // 249-250
        return sb.toString();
    }

    private String gerarParteAdministrativa(PacientePsicossocialDTO p) {
        StringBuilder sb = new StringBuilder();
        sb.append("        ");                                                    // 251-258 Data óbito/alta
        sb.append(RaasPaddingUtil.rightPad(p.getCidPrincipal(), TAM_CID));        // 259-262
        sb.append("    ".repeat(4));                                              // 263-278 CIDs secundários + causas
        sb.append("  ");                                                          // 279-280 Caráter (branco)
        sb.append(RaasPaddingUtil.rightPad(p.getOrigemPaciente(), TAM_ORIGEM));   // 281-282
        sb.append(RaasPaddingUtil.rightPad(p.getCoberturaEsf(), TAM_COBERTURA_ESF)); // 283
        sb.append(RaasPaddingUtil.rightPad(p.getCnesEsf(), TAM_CNES_ESF));        // 284-290
        sb.append(RaasPaddingUtil.leftPad(String.valueOf(p.getAcoes().size()), TAM_TOTAL_ACOES)); // 291-295
        sb.append(RaasPaddingUtil.rightPad(p.getDestinoPaciente(), TAM_DESTINO)); // 296-297
        sb.append(ORIGEM_INFORMACOES);                                            // 298-300
        sb.append(RaasPaddingUtil.rightPad(p.getSituacaoRua(), TAM_SITUACAO_RUA)); // 301
        sb.append(RaasPaddingUtil.rightPad(p.getUsuarioDrogas(), TAM_USUARIO_DROGA)); // 302
        sb.append(formatarTipoDroga(p));                                        // 303-305 Tipo droga
        return sb.toString();
    }

    private String gerarParteFinal(PacientePsicossocialDTO p) {
        StringBuilder sb = new StringBuilder();
        sb.append("             ");                                               // 306-318 Autorização
        sb.append(RaasPaddingUtil.rightPad(p.getDescricaoBairro(), TAM_BAIRRO));           // 319-348
        sb.append(RaasPaddingUtil.rightPad(p.getTipoLogradouro(), TAM_TIPO_LOGRADOURO)); // 349-351
        sb.append(RaasPaddingUtil.rightPad(p.getEmailPaciente(), TAM_EMAIL));     // 352-391
        sb.append(formatarCpf(p));                                                // 392-402
        sb.append(FILLER_4);                                                      // 403-406
        return sb.toString();
    }

    private String formatarCpf(PacientePsicossocialDTO p) {
        if (p.getCpfPaciente() != null && !p.getCpfPaciente().isBlank()) {
            return RaasPaddingUtil.leftPad(p.getCpfPaciente(), TAM_CPF);
        }
        return "00000000000";   // Zeros quando não tem CPF (conforme modelo)
    }

    private String formatarCns(PacientePsicossocialDTO p) {
        if (p.getCnsPaciente() != null && !p.getCnsPaciente().isBlank()) {
            return RaasPaddingUtil.rightPad(p.getCnsPaciente(), TAM_CNS);
        }
        return "000000000000000";
    }

    private String formatarTipoDroga(PacientePsicossocialDTO p) {
        if (!"S".equalsIgnoreCase(p.getUsuarioDrogas())) {
            return " ".repeat(TAM_TIPO_DROGA);
        }
        return RaasPaddingUtil.rightPad(p.getTipoDrogaAlcool(), 1)
                + RaasPaddingUtil.rightPad(p.getTipoDrogaCrack(), 1)
                + RaasPaddingUtil.rightPad(p.getTipoDrogaOutros(), 1);
    }

}
