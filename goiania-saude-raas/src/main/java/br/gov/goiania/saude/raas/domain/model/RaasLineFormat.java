package br.gov.goiania.saude.raas.domain.model;

public final class RaasLineFormat {

    private RaasLineFormat() { }

    public static final class Header {
        public static final int TAMANHO_LINHA = 159;
        public static final String CODIGO_LINHA = "01";
        public static final String IDENTIFICADOR_ARQUIVO = "#RAS#";
        public static final String INDICADOR_DESTINO = "M";
        public static final String CAMPO_RESERVADO = "          ";
        public static final String FINAL_DE_LINHA = "               ";

        public static final int TAM_COMPETENCIA = 6;
        public static final int TAM_QUANTIDADE_FOLHAS = 6;
        public static final int TAM_CAMPO_CONTROLE = 4;
        public static final int TAM_NOME_ORGAO_RESPONSAVEL = 30;
        public static final int TAM_SIGLA_ORGAO_RESPONSAVEL = 6;
        public static final int TAM_CNPJ_ORGAO_RESPONSAVEL = 14;
        public static final int TAM_NOME_ORGAO_DESTINO = 40;
        public static final int TAM_DATA_GERACAO = 8;
        public static final int TAM_VERSAO_SISTEMA = 5;
        public static final int TAM_VERSAO_BDSIA = 7;

        private Header() { }
    }

    public static final class Paciente {
        public static final int TAMANHO_LINHA = 406;
        public static final String CODIGO_LINHA = "15";
        public static final String NACIONALIDADE_BRASILEIRA = "010";
        public static final String ORIGEM_INFORMACOES = "EXT";
        public static final String FILLER_4 = "    ";
        public static final String MOTIVO_PERMANENCIA_PADRAO = "00";

        public static final int TAM_UF = 2;
        public static final int TAM_COMPETENCIA = 6;
        public static final int TAM_CNES = 7;
        public static final int TAM_CNS = 15;
        public static final int TAM_DATA = 8;
        public static final int TAM_NOME = 30;
        public static final int TAM_PRONTUARIO = 10;
        public static final int TAM_NOME_MAE = 30;
        public static final int TAM_LOGRADOURO = 30;
        public static final int TAM_NUMERO_END = 5;
        public static final int TAM_COMPLEMENTO = 10;
        public static final int TAM_CEP = 8;
        public static final int TAM_MUNICIPIO = 7;
        public static final int TAM_SEXO = 1;
        public static final int TAM_RACA = 2;
        public static final int TAM_RESPONSAVEL = 30;
        public static final int TAM_ETNIA = 4;
        public static final int TAM_TELEFONE = 11;
        public static final int TAM_CELULAR = 11;
        public static final int TAM_MOTIVO_SAIDA = 2;
        public static final int TAM_DATA_OBITO_ALTA = 8;
        public static final int TAM_CID = 4;
        public static final int TAM_ORIGEM = 2;
        public static final int TAM_CARATER = 2;
        public static final int TAM_ORIGEM_PACIENTE = 2;
        public static final int TAM_COBERTURA_ESF = 1;
        public static final int TAM_CNES_ESF = 7;
        public static final int TAM_TOTAL_ACOES = 5;
        public static final int TAM_DESTINO = 2;
        public static final int TAM_SITUACAO_RUA = 1;
        public static final int TAM_USUARIO_DROGA = 1;
        public static final int TAM_TIPO_DROGA = 3;
        public static final int TAM_AUTORIZACAO = 13;
        public static final int TAM_BAIRRO = 30;
        public static final int TAM_TIPO_LOGRADOURO = 3;
        public static final int TAM_EMAIL = 40;
        public static final int TAM_CPF = 11;
        public static final int TAM_FILLER_FINAL = 4;

        private Paciente() { }
    }

    public static final class Acao {
        public static final int TAMANHO_LINHA = 110;
        public static final String CODIGO_LINHA = "16";
        public static final String ORIGEM_INFORMACOES = "EXT";
        public static final String FILLER_4 = "    ";

        public static final int TAM_UF = 2;
        public static final int TAM_COMPETENCIA = 6;
        public static final int TAM_CNES = 7;
        public static final int TAM_CNS = 15;
        public static final int TAM_DATA = 8;
        public static final int TAM_PROCEDIMENTO = 10;
        public static final int TAM_CBO = 6;
        public static final int TAM_CNS_PROFISSIONAL = 15;
        public static final int TAM_SERVICO = 3;
        public static final int TAM_CLASSIFICACAO = 3;
        public static final int TAM_QUANTIDADE = 6;
        public static final int TAM_LOCAL = 1;
        public static final int TAM_CPF = 11;

        private Acao() { }
    }

    public static final String LINE_SEPARATOR = "\r\n";
    public static final int DIVISOR_CONTROLE = 1111;

    public static final String FORMATO_DATA = "yyyyMMdd";
    public static final String FORMATO_COMPETENCIA_HEADER = "yyyyMM";
}

