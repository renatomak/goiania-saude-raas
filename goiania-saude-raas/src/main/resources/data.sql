-- ============================================================================
--  data.sql — Bootstrap completo do ambiente LOCAL (PostgreSQL)
--  Projeto : goiania-saude-raas
--  Banco   : goiania_saude_local
--  Usuário : local
--  Senha   : local
--  Schema  : goiania_saude  (igual ao default_schema do application.yml)
--
--  COMO EXECUTAR (a partir de um usuário com privilégio de SUPERUSER):
--     psql -U postgres -h localhost -f data.sql
--
--  Em seguida, suba a aplicação com o profile "local":
--     mvn spring-boot:run -Dspring-boot.run.profiles=local
--     # ou
--     java -jar target/raas-1.0.0-SNAPSHOT.jar --spring.profiles.active=local
--
--  O script é IDEMPOTENTE — pode ser re-executado a qualquer momento.
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 0. Encerra conexões abertas, dropa banco e role (idempotência)
-- ----------------------------------------------------------------------------
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'goiania_saude_local'
  AND pid <> pg_backend_pid();

DROP DATABASE IF EXISTS goiania_saude_local;
DROP ROLE     IF EXISTS local;

-- ----------------------------------------------------------------------------
-- 1. Cria role / usuário da aplicação
-- ----------------------------------------------------------------------------
CREATE ROLE local WITH LOGIN PASSWORD 'local' CREATEDB;

-- ----------------------------------------------------------------------------
-- 2. Cria banco de dados
-- ----------------------------------------------------------------------------
CREATE DATABASE goiania_saude_local
    WITH OWNER     = local
         ENCODING  = 'UTF8'
         TEMPLATE  = template0
         LC_COLLATE = 'C'
         LC_CTYPE   = 'C';

COMMENT ON DATABASE goiania_saude_local IS
    'Banco local para testes da aplicação goiania-saude-raas';

-- ----------------------------------------------------------------------------
-- 3. Conecta no novo banco
-- ----------------------------------------------------------------------------
\connect goiania_saude_local

-- ----------------------------------------------------------------------------
-- 4. Cria schema goiania_saude (mesmo nome do default_schema da aplicação)
-- ----------------------------------------------------------------------------
CREATE SCHEMA IF NOT EXISTS goiania_saude AUTHORIZATION local;
GRANT ALL ON SCHEMA goiania_saude TO local;

SET search_path TO goiania_saude, public;

-- ============================================================================
-- 5. DROP TABLES (ordem inversa para evitar dependência)
-- ============================================================================
DROP TABLE IF EXISTS goiania_saude.raas_psi_item            CASCADE;
DROP TABLE IF EXISTS goiania_saude.raas_psi                 CASCADE;
DROP TABLE IF EXISTS goiania_saude.raas                     CASCADE;
DROP TABLE IF EXISTS goiania_saude.raas_processo            CASCADE;

DROP TABLE IF EXISTS goiania_saude.rnds_integracao_vacina   CASCADE;
DROP TABLE IF EXISTS goiania_saude.vac_aplicacao            CASCADE;
DROP TABLE IF EXISTS goiania_saude.atendimento_prontuario   CASCADE;
DROP TABLE IF EXISTS goiania_saude.aih                      CASCADE;
DROP TABLE IF EXISTS goiania_saude.atendimento              CASCADE;
DROP TABLE IF EXISTS goiania_saude.natureza_procura_tp_atendimento CASCADE;
DROP TABLE IF EXISTS goiania_saude.tipo_atendimento         CASCADE;
DROP TABLE IF EXISTS goiania_saude.classificacao_risco      CASCADE;
DROP TABLE IF EXISTS goiania_saude.tabela_cbo               CASCADE;
DROP TABLE IF EXISTS goiania_saude.produto_vacina           CASCADE;
DROP TABLE IF EXISTS goiania_saude.produtos                 CASCADE;
DROP TABLE IF EXISTS goiania_saude.fabricante_medicamento   CASCADE;
DROP TABLE IF EXISTS goiania_saude.grupo_atendimento_vacinacao_esus CASCADE;
DROP TABLE IF EXISTS goiania_saude.local_aplicacao          CASCADE;
DROP TABLE IF EXISTS goiania_saude.via_administracao        CASCADE;
DROP TABLE IF EXISTS goiania_saude.profissional             CASCADE;
DROP TABLE IF EXISTS goiania_saude.orgao_emissor            CASCADE;
DROP TABLE IF EXISTS goiania_saude.empresa                  CASCADE;
DROP TABLE IF EXISTS goiania_saude.calendario               CASCADE;
DROP TABLE IF EXISTS goiania_saude.tipo_vacina              CASCADE;
DROP TABLE IF EXISTS goiania_saude.usuario_cadsus           CASCADE;
DROP TABLE IF EXISTS goiania_saude.endereco_usuario_cadsus  CASCADE;
DROP TABLE IF EXISTS goiania_saude.tipo_logradouro_cadsus   CASCADE;
DROP TABLE IF EXISTS goiania_saude.nacionalidade            CASCADE;
DROP TABLE IF EXISTS goiania_saude.etnia_indigena           CASCADE;
DROP TABLE IF EXISTS goiania_saude.raca                     CASCADE;
DROP TABLE IF EXISTS goiania_saude.cidade                   CASCADE;
DROP TABLE IF EXISTS goiania_saude.estado                   CASCADE;

-- ============================================================================
-- 6. TABELAS LEGADAS (mantidas para compatibilidade com scripts anteriores)
-- ============================================================================

CREATE TABLE estado (
    cod_est   INTEGER PRIMARY KEY,
    sigla     VARCHAR(2) NOT NULL
);

CREATE TABLE cidade (
    cod_cid   BIGINT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    cod_est   INTEGER NOT NULL
);

CREATE TABLE tipo_logradouro_cadsus (
    cd_tipo_logradouro INTEGER PRIMARY KEY,
    ds_tipo_logradouro VARCHAR(50) NOT NULL
);

CREATE TABLE endereco_usuario_cadsus (
    cd_endereco        BIGINT PRIMARY KEY,
    keyword            VARCHAR(10),
    nm_logradouro      VARCHAR(100),
    nm_comp_logradouro VARCHAR(50),
    nr_logradouro      VARCHAR(10),
    cep                VARCHAR(10),
    nm_bairro          VARCHAR(50),
    cod_cid            BIGINT  NOT NULL,
    cd_tipo_logradouro INTEGER NOT NULL
);

CREATE TABLE raca (
    cd_raca     SMALLINT PRIMARY KEY,
    ds_raca     VARCHAR(30) NOT NULL,
    "version"   BIGINT DEFAULT 0 NOT NULL,
    version_all BIGINT NOT NULL
);

CREATE TABLE etnia_indigena (
    cd_etnia    BIGINT PRIMARY KEY,
    ds_etnia    VARCHAR(100) NOT NULL,
    "version"   BIGINT NOT NULL,
    version_all BIGINT NOT NULL
);

CREATE TABLE nacionalidade (
    cd_pais     INTEGER PRIMARY KEY,
    ds_pais     VARCHAR(50) NOT NULL,
    "version"   BIGINT DEFAULT 0 NOT NULL,
    version_all BIGINT NOT NULL
);

CREATE TABLE usuario_cadsus (
    cd_usu_cadsus            NUMERIC(8)  PRIMARY KEY,
    nm_usuario               VARCHAR(70) NOT NULL,
    sg_sexo                  CHAR(1)     NOT NULL,
    nm_mae                   VARCHAR(70),
    nm_pai                   VARCHAR(70),
    email                    VARCHAR(100),
    cpf                      VARCHAR(14),
    dt_nascimento            DATE        NOT NULL,
    cd_pais_nascimento       INTEGER,
    cd_raca                  SMALLINT,
    cd_etnia                 BIGINT,
    nr_telefone              VARCHAR(15),
    nr_telefone_2            VARCHAR(15),
    apelido                  VARCHAR(50),
    cod_cid_nascimento       BIGINT,
    cd_endereco              BIGINT,
    dt_inclusao              DATE        NOT NULL DEFAULT CURRENT_DATE,
    dt_preenchimento_form    DATE        NOT NULL DEFAULT CURRENT_DATE,
    st_excluido              SMALLINT    NOT NULL DEFAULT 0,
    "version"                BIGINT      DEFAULT 0 NOT NULL,
    dt_usuario               TIMESTAMP   NOT NULL DEFAULT NOW(),
    cd_usuario               NUMERIC(6)  NOT NULL DEFAULT 1,
    cd_usuario_cad           NUMERIC(6)  NOT NULL DEFAULT 1,
    flag_utiliza_nome_social SMALLINT    NOT NULL DEFAULT 0,
    flag_unificado           SMALLINT    NOT NULL DEFAULT 0,
    flag_outras_pop_nomades  SMALLINT    NOT NULL DEFAULT 0,
    version_all              BIGINT      NOT NULL DEFAULT 1
);

CREATE TABLE tipo_vacina (
    cd_vacina   INTEGER PRIMARY KEY,
    ds_vacina   VARCHAR(200) NOT NULL,
    cod_gru     INTEGER      NOT NULL,
    cod_sub     INTEGER      NOT NULL,
    "version"   BIGINT       DEFAULT 0 NOT NULL,
    version_all BIGINT       DEFAULT 1 NOT NULL
);

CREATE TABLE calendario (
    cd_calendario      BIGINT PRIMARY KEY,
    ds_calendario      VARCHAR(50) NOT NULL,
    "version"          BIGINT,
    padrao             CHAR(1)  NOT NULL DEFAULT 'S',
    flag_atualizacao   SMALLINT NOT NULL DEFAULT 0,
    flag_aprazamento   SMALLINT NOT NULL DEFAULT 0,
    cod_estrategia_pni SMALLINT NOT NULL DEFAULT 0
);

CREATE TABLE empresa (
    empresa           INTEGER PRIMARY KEY,
    descricao         VARCHAR(120) NOT NULL,
    fantasia          VARCHAR(120),
    cod_cid           BIGINT      NOT NULL DEFAULT 1,
    "version"         BIGINT      DEFAULT 0 NOT NULL,
    cnes              VARCHAR(7),
    acesso_restrito   SMALLINT    NOT NULL DEFAULT 0,
    situacao_bloqueio SMALLINT    NOT NULL DEFAULT 0,
    telefone          VARCHAR(20)
);

CREATE TABLE orgao_emissor (
    cd_orgao_emissor SMALLINT PRIMARY KEY,
    ds_orgao_emissor VARCHAR(60) NOT NULL,
    sg_orgao_emissor VARCHAR(10),
    "version"        BIGINT      DEFAULT 0 NOT NULL,
    version_all      BIGINT      NOT NULL DEFAULT 1
);

CREATE TABLE profissional (
    cd_profissional INTEGER PRIMARY KEY,
    nm_profissional VARCHAR(60) NOT NULL,
    nr_registro     VARCHAR(50),
    cd_cns          VARCHAR(60),
    cd_con_classe   SMALLINT,
    "version"       BIGINT NOT NULL DEFAULT 0,
    version_all     BIGINT NOT NULL DEFAULT 1
);

CREATE TABLE via_administracao (
    cd_via_administracao BIGINT PRIMARY KEY,
    descricao            VARCHAR NOT NULL,
    "version"            BIGINT  NOT NULL DEFAULT 1
);

CREATE TABLE local_aplicacao (
    cd_local_aplicacao BIGINT PRIMARY KEY,
    descricao          VARCHAR NOT NULL,
    "version"          BIGINT  NOT NULL DEFAULT 1
);

CREATE TABLE grupo_atendimento_vacinacao_esus (
    cd_grupo_atendimento_vac_esus BIGINT PRIMARY KEY,
    descricao                     VARCHAR NOT NULL,
    "version"                     BIGINT  NOT NULL DEFAULT 1
);

CREATE TABLE fabricante_medicamento (
    cd_fabricante INTEGER PRIMARY KEY,
    ds_fabricante VARCHAR(50) NOT NULL,
    "version"     BIGINT DEFAULT 0 NOT NULL,
    cnpj          VARCHAR(15)
);

CREATE TABLE produtos (
    cod_pro                                VARCHAR(13) PRIMARY KEY,
    cod_uni                                NUMERIC(12) NOT NULL DEFAULT 1,
    cod_gru                                INTEGER     NOT NULL DEFAULT 1,
    cod_sub                                INTEGER     NOT NULL DEFAULT 1,
    descricao                              VARCHAR(200) NOT NULL,
    cont_min                               CHAR(1)     NOT NULL DEFAULT 'N',
    usuario                                INTEGER     NOT NULL DEFAULT 1,
    dt_usuario                             DATE        NOT NULL DEFAULT CURRENT_DATE,
    dt_cadastro                            DATE        NOT NULL DEFAULT CURRENT_DATE,
    "version"                              BIGINT      DEFAULT 0 NOT NULL,
    flag_permite_disp_mais                 VARCHAR(1)  NOT NULL DEFAULT 'N',
    flag_emprestimo                        SMALLINT    NOT NULL DEFAULT 0,
    flag_tratamento_prolongado_antibiotico SMALLINT    NOT NULL DEFAULT 0,
    flag_ativo                             SMALLINT    NOT NULL DEFAULT 1,
    flag_disp_ped_lic                      SMALLINT    NOT NULL DEFAULT 0,
    cd_fabricante                          INTEGER,
    fabricante_esus                        VARCHAR(150)
);

CREATE TABLE produto_vacina (
    cd_produto_vacina BIGINT PRIMARY KEY,
    cod_pro           VARCHAR(13) NOT NULL,
    cd_vacina         INTEGER     NOT NULL,
    qt_dose           SMALLINT    NOT NULL DEFAULT 1,
    "version"         BIGINT      DEFAULT 0 NOT NULL
);

CREATE TABLE tabela_cbo (
    cd_cbo  VARCHAR(10) PRIMARY KEY,
    ds_cbo  VARCHAR(200) NOT NULL
);

CREATE TABLE classificacao_risco (
    cd_classificacao_risco INTEGER PRIMARY KEY,
    descricao              VARCHAR(100) NOT NULL
);

CREATE TABLE tipo_atendimento (
    cd_tp_atendimento   INTEGER PRIMARY KEY,
    ds_tipo_atendimento VARCHAR(200) NOT NULL
);

CREATE TABLE natureza_procura_tp_atendimento (
    cd_nat_proc_tp_atendimento INTEGER PRIMARY KEY,
    cd_tp_atendimento          INTEGER NOT NULL
);

CREATE TABLE atendimento (
    nr_atendimento             BIGINT     PRIMARY KEY,
    cd_usu_cadsus              NUMERIC(8) NOT NULL,
    empresa                    INTEGER    NOT NULL,
    cd_profissional            INTEGER,
    cd_cbo                     VARCHAR(10),
    classificacao_risco        INTEGER,
    cd_nat_proc_tp_atendimento INTEGER,
    dt_chegada                 TIMESTAMP,
    dt_atendimento             TIMESTAMP,
    status                     SMALLINT   NOT NULL DEFAULT 5
);

CREATE TABLE atendimento_prontuario (
    id             BIGINT    PRIMARY KEY,
    nr_atendimento BIGINT    NOT NULL,
    data           TIMESTAMP NOT NULL,
    tipo_registro  SMALLINT  NOT NULL,
    descricao      TEXT
);

CREATE TABLE aih (
    nr_atendimento        BIGINT    PRIMARY KEY,
    cd_profissional       INTEGER,
    dt_cadastro           TIMESTAMP,
    principais_sinais     TEXT,
    condicoes_just_intern TEXT,
    principais_resultados TEXT,
    diagnostico_inicial   TEXT
);

CREATE TABLE vac_aplicacao (
    cd_vac_aplicacao          BIGINT      PRIMARY KEY,
    cd_usu_cadsus             NUMERIC(8)  NOT NULL,
    cd_vacina                 INTEGER,
    ds_vacina                 VARCHAR(200) NOT NULL,
    cd_usuario                NUMERIC(6)  NOT NULL DEFAULT 1,
    dt_aplicacao              TIMESTAMP,
    dt_cadastro               TIMESTAMP   NOT NULL DEFAULT NOW(),
    status                    SMALLINT    NOT NULL DEFAULT 0,
    observacao                VARCHAR(1024),
    cd_calendario             BIGINT,
    cd_estrategia             BIGINT,
    lote                      VARCHAR(20),
    cd_produto_vacina         BIGINT,
    empresa                   INTEGER     NOT NULL,
    dt_validade               TIMESTAMP,
    nr_atendimento            BIGINT,
    cd_profissional_aplicacao INTEGER,
    grupo_atendimento         SMALLINT    NOT NULL DEFAULT 0,
    flag_gestante             SMALLINT    NOT NULL DEFAULT 0,
    novo_frasco               SMALLINT    NOT NULL DEFAULT 0,
    cd_doses                  SMALLINT,
    flag_historico            SMALLINT    NOT NULL DEFAULT 0,
    flag_puerpera             SMALLINT,
    flag_fora_esquema_vacinal SMALLINT    NOT NULL DEFAULT 0,
    turno                     SMALLINT,
    local_atendimento         SMALLINT,
    viajante                  SMALLINT,
    cd_via_administracao      BIGINT,
    cd_local_aplicacao        BIGINT,
    "version"                 BIGINT      NOT NULL DEFAULT 1,
    version_all               BIGINT,
    comunicante_hanseniase    SMALLINT    NOT NULL DEFAULT 0,
    status_baixa              SMALLINT    NOT NULL DEFAULT 0
);

CREATE TABLE rnds_integracao_vacina (
    cd_rnds_integracao_vacina BIGINT     PRIMARY KEY,
    cd_vac_aplicacao          BIGINT     NOT NULL,
    uuid_rnds                 VARCHAR,
    uuid_origem               VARCHAR    NOT NULL DEFAULT '',
    situacao                  SMALLINT   NOT NULL DEFAULT 0,
    cd_usuario                NUMERIC(6) NOT NULL DEFAULT 1,
    dt_usuario                TIMESTAMP  NOT NULL DEFAULT NOW(),
    "version"                 BIGINT     NOT NULL DEFAULT 1
);

-- ============================================================================
-- 7. TABELAS USADAS PELA APLICAÇÃO RAAS
--    (alinhadas com as entidades JPA / native queries dos repositórios)
-- ============================================================================

-- 7.1 raas — cabeçalho da remessa (RaasEntity / RaasHeaderProjection)
CREATE TABLE raas (
    cd_raas                  BIGINT       PRIMARY KEY,
    linha                    INTEGER,
    indicador_inicio         VARCHAR(10),
    competencia              DATE         NOT NULL,
    quantidade_folhas        BIGINT,
    campo_controle           BIGINT,
    nm_orgao_origem          VARCHAR(100),
    sigla_orgao_origem       VARCHAR(20),
    cgc_prestador            BIGINT,
    nm_orgao_destino         VARCHAR(100),
    indicador_orgao_destino  VARCHAR(10),
    dt_geracao               DATE,
    versao                   VARCHAR(20),
    versao_bdsia             VARCHAR(20),
    status                   INTEGER      NOT NULL DEFAULT 1,
    empresa                  BIGINT
);

CREATE INDEX idx_raas_competencia ON raas (competencia);
CREATE INDEX idx_raas_empresa     ON raas (empresa);

-- 7.2 raas_psi — paciente psicossocial (RaasPsiEntity / RaasPsiPacienteProjection)
CREATE TABLE raas_psi (
    cd_raas_psi                BIGINT        PRIMARY KEY,
    cd_raas                    BIGINT,
    linha                      INTEGER,
    unidade_federacao          INTEGER,
    competencia                DATE          NOT NULL,
    unidade_prestadora_servico INTEGER,
    cartao_nacional_saude      VARCHAR(15),
    dt_inicio_validade         DATE,
    dt_final_validade          DATE,
    nm_paciente                VARCHAR(200),
    numero_prontuario          INTEGER,
    nm_mae                     VARCHAR(200),
    logradouro                 VARCHAR(200),
    numero_logradouro          VARCHAR(10),
    complemento_logradouro     VARCHAR(50),
    cep                        VARCHAR(10),
    municipio                  INTEGER,
    dt_nascimento              DATE,
    sexo                       CHAR(1),
    raca                       INTEGER,
    nm_responsavel             VARCHAR(200),
    nacionalidade              INTEGER,
    etnia                      INTEGER,
    telefone                   VARCHAR(15),
    celular                    VARCHAR(15),
    motivo_saida_permanencia   INTEGER,
    dt_ocorrencia              DATE,
    cid_principal              VARCHAR(10),
    cid_secundario_1           VARCHAR(10),
    cid_secundario_2           VARCHAR(10),
    cid_secundario_3           VARCHAR(10),
    cid_causas_associadas      VARCHAR(10),
    carater_atendimento        INTEGER,
    origem_paciente            INTEGER,
    cobertura_esf              VARCHAR(1),
    codigo_cobertura_esf       INTEGER,
    total_procedimentos        INTEGER,
    destino_paciente           INTEGER,
    origem_informacoes         VARCHAR(10),
    situacao_rua               VARCHAR(1),
    usuario_drogas             VARCHAR(1),
    tipo_droga_alcool          VARCHAR(1),
    tipo_droga_crack           VARCHAR(1),
    tipo_droga_outros          VARCHAR(1),
    numero_autorizacao         BIGINT,
    descricao_bairro           VARCHAR(100),
    tipo_logradouro            INTEGER,
    email_paciente             VARCHAR(100),
    cpf_paciente               VARCHAR(14),
    CONSTRAINT fk_raas_psi_raas FOREIGN KEY (cd_raas)
        REFERENCES raas (cd_raas) ON DELETE SET NULL
);

CREATE INDEX idx_raas_psi_competencia ON raas_psi (competencia);
CREATE INDEX idx_raas_psi_cd_raas     ON raas_psi (cd_raas);
CREATE INDEX idx_raas_psi_cnes        ON raas_psi (unidade_prestadora_servico);

-- 7.3 raas_psi_item — procedimentos do paciente (RaasPsiItemEntity / RaasPsiItemProjection)
CREATE TABLE raas_psi_item (
    cd_raas_psi_item           BIGINT       PRIMARY KEY,
    cd_raas_psi                BIGINT       NOT NULL,
    linha                      INTEGER,
    unidade_federacao          INTEGER,
    competencia                DATE,
    unidade_prestadora_servico INTEGER,
    cartao_nacional_saude      VARCHAR(15),
    dt_inicio_validade         DATE,
    cod_procedimento           BIGINT,
    cod_cbo_executante         VARCHAR(10),
    cns_executante             VARCHAR(15),
    dt_execucao_procedimento   DATE,
    servico                    INTEGER,
    classificacao              INTEGER,
    quantidade_realizada       INTEGER,
    origem_informacoes         VARCHAR(10),
    local_realizacao           VARCHAR(1),
    cpf_paciente               VARCHAR(14),
    CONSTRAINT fk_raas_psi_item_paciente FOREIGN KEY (cd_raas_psi)
        REFERENCES raas_psi (cd_raas_psi) ON DELETE CASCADE
);

CREATE INDEX idx_raas_psi_item_cd_raas_psi ON raas_psi_item (cd_raas_psi);
CREATE INDEX idx_raas_psi_item_competencia ON raas_psi_item (competencia);

-- 7.4 raas_processo — arquivos RAAS gerados (ArquivosRaasEntity)
CREATE TABLE raas_processo (
    cd_raas_processo BIGSERIAL    PRIMARY KEY,
    mes              INTEGER      NOT NULL,
    ano              INTEGER      NOT NULL,
    dt_geracao       DATE,
    empresa          BIGINT       NOT NULL,
    nome_empresa     VARCHAR(200),
    path             VARCHAR(255),
    status           VARCHAR(2)   NOT NULL,
    total_folha      NUMERIC(15,2),
    texto            TEXT
);

CREATE INDEX idx_raas_processo_empresa ON raas_processo (empresa);
CREATE INDEX idx_raas_processo_periodo ON raas_processo (ano, mes);

-- ============================================================================
-- 8. INSERTS — DADOS BASE (legados)
-- ============================================================================

INSERT INTO estado (cod_est, sigla) VALUES
    (1, 'GO'), (2, 'SP'), (3, 'RJ');

INSERT INTO cidade (cod_cid, descricao, cod_est) VALUES
    (151,    'GOIANIA',              1),
    (152,    'APARECIDA DE GOIANIA', 1),
    (200,    'SAO PAULO',            2),
    (520870, 'GOIANIA',              1);

INSERT INTO tipo_logradouro_cadsus (cd_tipo_logradouro, ds_tipo_logradouro) VALUES
    (81, 'RUA'),
    (82, 'AVENIDA'),
    (83, 'TRAVESSA');

INSERT INTO endereco_usuario_cadsus
    (cd_endereco, keyword, nm_logradouro, nm_comp_logradouro, nr_logradouro, cep, nm_bairro, cod_cid, cd_tipo_logradouro)
VALUES
    (7230412, 'K1', 'C 149',           'QD 325 LT 11',                  'SN',   '74230050', 'JARDIM AMERICA',                151,    81),
    (7230413, 'K2', 'AVENIDA GOIAS',    NULL,                            '1500', '74000100', 'CENTRO',                        151,    82),
    (7230414, 'K3', 'CV18',             'RESIDENCIAL CENTER VILLE',     'SN',   '74905450', 'RESIDENCIAL CENTER VILLE',      520870, 81),
    (7230415, 'K4', 'RB 51 A',          'QD 51 LT 72',                   'SN',   '74474396', 'Residencial Recanto do Bosque', 520870, 81);

INSERT INTO raca (cd_raca, ds_raca, "version", version_all) VALUES
    (1, 'BRANCA', 1, 1),
    (2, 'PRETA', 1, 2),
    (3, 'AMARELA', 1, 3),
    (4, 'PARDA', 1, 4),
    (5, 'INDIGENA', 1, 5);

INSERT INTO etnia_indigena (cd_etnia, ds_etnia, "version", version_all) VALUES
    (1, 'Nao Informada', 1, 1),
    (2, 'Guarani',       1, 2),
    (3, 'Tupi',          1, 3);

INSERT INTO nacionalidade (cd_pais, ds_pais, "version", version_all) VALUES
    (1, 'BRASIL',    1, 1),
    (10, 'BRASILEIRO',1, 2),
    (2, 'ARGENTINA', 1, 3),
    (3, 'PORTUGAL',  1, 4);

INSERT INTO usuario_cadsus (
    cd_usu_cadsus, nm_usuario, sg_sexo, nm_mae, nm_pai, email, cpf, dt_nascimento,
    cd_pais_nascimento, cd_raca, cd_etnia, nr_telefone, nr_telefone_2, apelido,
    cod_cid_nascimento, cd_endereco
) VALUES
    (3033700, 'MARIA EDUARDA HUMMEL OLIVEIRA',     'F',
     'ANA PAULA HUMMEL OLIVEIRA', 'ADRIANO DE OLIVEIRA',
     'mariaeduarda@email.com', '05342621180', '2006-05-18',
     1, 1, 1, '62991632742', '62991632743', 'MARIA EDUARDA', 151, 7230412),
    (5128061, 'DORVALINA FERREIRA DA MOTA MOCK',  'F',
     'FRANCISCA FERREIRA DA MOTA', NULL,
     NULL, '96748257115', '1952-11-11',
     1, 4, NULL, '62984061234', NULL, NULL, 520870, 7230414),
    (9282479, 'THALLYA VALENTINA GOMES RAMOS MOCK', 'F',
     'NAYANNE GOMES SODRE RAMOS', 'MACKSON DE SOUSA RAMOS',
     NULL, '12169441131', '2024-06-30',
     1, 4, NULL, '62998702201', NULL, NULL, 520870, 7230415);

INSERT INTO tipo_vacina (cd_vacina, ds_vacina, cod_gru, cod_sub, "version", version_all) VALUES
    (1, 'COVID-19', 1, 1, 1, 1),
    (2, 'INFLUENZA', 1, 2, 1, 2),
    (3, 'FEBRE AMARELA', 1, 3, 1, 3),
    (4, 'HEPATITE B', 2, 1, 1, 4),
    (5, 'TETANO', 2, 2, 1, 5);

INSERT INTO calendario (cd_calendario, ds_calendario, "version", padrao, flag_atualizacao, flag_aprazamento, cod_estrategia_pni) VALUES
    (1, 'Rotina',   1, 'S', 0, 0, 1),
    (2, 'Campanha', 1, 'N', 0, 0, 2),
    (3, 'Especial', 1, 'N', 0, 0, 3);

INSERT INTO fabricante_medicamento (cd_fabricante, ds_fabricante, "version", cnpj) VALUES
    (1, 'PFIZER',          1, '58006628000120'),
    (2, 'ASTRAZENECA',     1, '60318797000117'),
    (3, 'BUTANTAN',        1, '53373484000113'),
    (4, 'BIO-MANGUINHOS',  1, '33781055000135'),
    (5, 'FUNDACAO BUTANTAN', 1, '60501293000176');

INSERT INTO produtos (
    cod_pro, cod_uni, cod_gru, cod_sub, descricao, cont_min,
    usuario, dt_usuario, dt_cadastro, "version",
    flag_permite_disp_mais, flag_emprestimo, flag_tratamento_prolongado_antibiotico,
    flag_ativo, flag_disp_ped_lic, cd_fabricante, fabricante_esus
) VALUES
    ('COMIRNATY01', 1, 1, 1, 'COMIRNATY - COVID-19 BNT162b2',     'N', 1, CURRENT_DATE, CURRENT_DATE, 1, 'N', 0, 0, 1, 0, 1, 'Pfizer-BioNTech'),
    ('VACFLU01',    1, 1, 2, 'VACINA INFLUENZA TRIVALENTE',        'N', 1, CURRENT_DATE, CURRENT_DATE, 1, 'N', 0, 0, 1, 0, 3, 'Butantan'),
    ('FAMAMAR01',   1, 1, 3, 'VACINA FEBRE AMARELA 17DD ATENUADA', 'N', 1, CURRENT_DATE, CURRENT_DATE, 1, 'N', 0, 0, 1, 0, 4, 'Bio-Manguinhos');

INSERT INTO produto_vacina (cd_produto_vacina, cod_pro, cd_vacina, qt_dose, "version") VALUES
    (1, 'COMIRNATY01', 1, 2, 1),
    (2, 'VACFLU01',    2, 1, 1),
    (3, 'FAMAMAR01',   3, 1, 1);

INSERT INTO orgao_emissor (cd_orgao_emissor, ds_orgao_emissor, sg_orgao_emissor, "version", version_all) VALUES
    (1, 'CONSELHO FEDERAL DE MEDICINA',   'CRM',   1, 1),
    (2, 'CONSELHO FEDERAL DE ENFERMAGEM', 'COREN', 1, 2);

INSERT INTO profissional (cd_profissional, nm_profissional, nr_registro, cd_cns, cd_con_classe, "version", version_all) VALUES
    (1, 'DR. JOAO DA SILVA',     'CRM-GO 12345',   '123456789012345', 1, 1, 1),
    (2, 'ENF. MARIA SOUZA',      'COREN-GO 98765', '987654321098765', 2, 1, 2),
    (3, 'PSIC. CARLOS PEREIRA',  'CRP-GO 7777',    '700000000000777', 2, 1, 3),
    (4, 'TER. OCUP. ANA LIMA',   'CREFITO 8888',   '700000000000888', 2, 1, 4);

INSERT INTO via_administracao (cd_via_administracao, descricao, "version") VALUES
    (1, 'INTRAMUSCULAR', 1),
    (2, 'SUBCUTANEA',    1),
    (3, 'ORAL',          1),
    (4, 'INTRADERMICA',  1);

INSERT INTO local_aplicacao (cd_local_aplicacao, descricao, "version") VALUES
    (1, 'BRACO DIREITO',  1),
    (2, 'BRACO ESQUERDO', 1),
    (3, 'COXA DIREITA',   1),
    (4, 'COXA ESQUERDA',  1);

INSERT INTO grupo_atendimento_vacinacao_esus (cd_grupo_atendimento_vac_esus, descricao, "version") VALUES
    (1, 'POPULACAO GERAL',        1),
    (2, 'IDOSOS',                 1),
    (3, 'GESTANTES',              1),
    (4, 'PROFISSIONAIS DE SAUDE', 1);

INSERT INTO tabela_cbo (cd_cbo, ds_cbo) VALUES
    ('225125', 'MEDICO CLINICO'),
    ('225170', 'MEDICO PSIQUIATRA'),
    ('223505', 'ENFERMEIRO'),
    ('251510', 'PSICOLOGO CLINICO'),
    ('322205', 'TECNICO DE ENFERMAGEM');

INSERT INTO classificacao_risco (cd_classificacao_risco, descricao) VALUES
    (1, 'Não Urgente'),
    (2, 'Pouco Urgente'),
    (3, 'Urgente'),
    (4, 'Muito Urgente'),
    (5, 'Emergência');

INSERT INTO tipo_atendimento (cd_tp_atendimento, ds_tipo_atendimento) VALUES
    (1, 'APS - CONSULTA DE ENFERMAGEM'),
    (2, 'APS - CONSULTA MÉDICA'),
    (3, 'CAPS - ACOLHIMENTO PSICOSSOCIAL'),
    (4, 'CAPS - ATENDIMENTO INDIVIDUAL'),
    (5, 'CAPS - ATENDIMENTO EM GRUPO');

INSERT INTO natureza_procura_tp_atendimento (cd_nat_proc_tp_atendimento, cd_tp_atendimento) VALUES
    (1, 1), (2, 2), (3, 3), (4, 4), (5, 5);

-- Empresas (CAPS — unidades prestadoras de serviço psicossocial de Goiânia)
-- Esses são os 12 CAPS reais que devem aparecer no endpoint GET /v1/unidades
INSERT INTO empresa (empresa, descricao, fantasia, cod_cid, "version", cnes, acesso_restrito, situacao_bloqueio, telefone) VALUES
    (   249644, 'CAPS AD III IPE',                                          'CAPS AD III IPE',        520870, 1, '2337693', 0, 0, '6235241001'),
    (101347976, 'CAPS AD OESTE',                                            'CAPS AD OESTE',          520870, 1, '7018969', 0, 0, '6235241002'),
    (   249646, 'CAPS AD3 NOROESTE',                                        'CAPS AD3 NOROESTE',      520870, 1, '7018977', 0, 0, '6235243006'),
    ( 64102343, 'CAPS LIBERDADE',                                           'CAPS LIBERDADE',         520870, 1, '6928287', 0, 0, '6235241003'),
    (   249922, 'CENTRO DE ATENCAO A SAUDE DE ALCOOLISTAS E TOXIC CAPSAD',  'CAPSAD CENTRO',          520870, 1, '2337707', 0, 0, '6235241004'),
    (   249924, 'CENTRO DE ATENCAO PSICOSSOCIAL AGUA VIVA',                 'CAPS AGUA VIVA',         520870, 1, '2337715', 0, 0, '6235241005'),
    (   249926, 'CENTRO DE ATENCAO PSICOSSOCIAL BEIJA FLOR',                'CAPS BEIJA FLOR',        520870, 1, '2337723', 0, 0, '6235241006'),
    (   249928, 'CENTRO DE ATENCAO PSICOSSOCIAL ESPERANCA',                 'CAPS ESPERANCA',         520870, 1, '2337731', 0, 0, '6235241007'),
    (   249930, 'CENTRO DE ATENCAO PSICOSSOCIAL GIRASSOL',                  'CAPS GIRASSOL',          520870, 1, '2337758', 0, 0, '6235241008'),
    (   249932, 'CENTRO DE ATENCAO PSICOSSOCIAL NOVO MUNDO',                'CAPS NOVO MUNDO',        520870, 1, '2337766', 0, 0, '6235241009'),
    (   249934, 'CENTRO DE ATENCAO PSICOSSOCIAL VIDA',                      'CAPS VIDA',              520870, 1, '2337774', 0, 0, '6235241010'),
    (103205682, 'REDE CAPSI CATIVAR',                                       'CAPSI CATIVAR',          520870, 1, '9876543', 0, 0, '6235241011');

-- Atendimentos e prontuário (mínimo para os scripts legados continuarem coerentes)
INSERT INTO atendimento (nr_atendimento, cd_usu_cadsus, empresa, cd_profissional, cd_cbo, classificacao_risco, cd_nat_proc_tp_atendimento, dt_chegada, dt_atendimento, status) VALUES
    (500001, 9282479, 249646, 3, '251510', 1, 3, '2025-12-02 08:00:00', '2025-12-02 08:30:00', 5),
    (500002, 5128061, 249932, 4, '225170', 1, 4, '2025-12-04 09:00:00', '2025-12-04 09:30:00', 5);

INSERT INTO atendimento_prontuario (id, nr_atendimento, data, tipo_registro, descricao) VALUES
    (1, 500001, '2025-12-02 08:30:00', 1, 'Acolhimento psicossocial inicial — paciente em uso de SPA.'),
    (2, 500002, '2025-12-04 09:30:00', 2, 'Atendimento individual de seguimento. Estável.');

-- ============================================================================
-- 9. INSERTS — DADOS NÚCLEO RAAS PSICOSSOCIAL
--    (objeto principal dos testes desta aplicação)
-- ============================================================================

-- 9.1 Cabeçalhos RAAS — duas competências para testar o endpoint de geração
INSERT INTO raas (
    cd_raas, linha, indicador_inicio, competencia, quantidade_folhas, campo_controle,
    nm_orgao_origem, sigla_orgao_origem, cgc_prestador,
    nm_orgao_destino, indicador_orgao_destino,
    dt_geracao, versao, versao_bdsia, status, empresa
) VALUES
    (1, 1, '01', '2025-12-01', 1, 100001,
     'FATURAMENTO SMS', 'GCPAH', 25141524000123,
     'SECRETARIA MUN DE SAUDE DE GOIANIA', 'M',
     '2025-12-31', '1.0', '202512a', 1, 249646),
    (2, 1, '01', '2026-01-01', 1, 100002,
     'FATURAMENTO SMS', 'GCPAH', 25141524000123,
     'SECRETARIA MUN DE SAUDE DE GOIANIA', 'M',
     '2026-01-31', '1.0', '202601a', 1, 249646);

-- 9.2 Pacientes RAAS PSI — 5 pacientes na competência 12/2025, 3 em 01/2026
INSERT INTO raas_psi (
    cd_raas_psi, cd_raas, linha, unidade_federacao, competencia, unidade_prestadora_servico,
    cartao_nacional_saude, dt_inicio_validade, dt_final_validade,
    nm_paciente, numero_prontuario, nm_mae,
    logradouro, numero_logradouro, complemento_logradouro, cep, municipio,
    dt_nascimento, sexo, raca, nm_responsavel, nacionalidade, etnia,
    telefone, celular, motivo_saida_permanencia, dt_ocorrencia,
    cid_principal, cid_secundario_1, cid_secundario_2, cid_secundario_3, cid_causas_associadas,
    carater_atendimento, origem_paciente, cobertura_esf, codigo_cobertura_esf,
    total_procedimentos, destino_paciente, origem_informacoes,
    situacao_rua, usuario_drogas, tipo_droga_alcool, tipo_droga_crack, tipo_droga_outros,
    numero_autorizacao, descricao_bairro, tipo_logradouro, email_paciente, cpf_paciente
) VALUES
    -- Competência 2025-12 (5 pacientes)
    (101, 1, 1, 52, '2025-12-01', 7018977, '700000000000001', '2025-12-01', '2025-12-31',
     'PACIENTE TESTE UM',                    1001, 'MARIA TESTE UM',
     'RUA C 149',           'SN',   'QD 325 LT 11',         '74230050', 520870,
     '1985-04-12', 'M', 1, NULL, 10, 0, '6232110001', '62991630001', 0, '2025-12-15',
     'F19.2', NULL, NULL, NULL, NULL,
     1, 1, 'S', 7018977, 3, 1, 'BPA',
     'N', 'S', 'S', 'N', 'N',
     202512000001, 'JARDIM AMERICA', 81, 'paciente1@email.com', '00000000001'),
    (102, 1, 2, 52, '2025-12-01', 7018977, '700000000000002', '2025-12-01', '2025-12-31',
     'PACIENTE TESTE DOIS',                  1002, 'MARIA TESTE DOIS',
     'AVENIDA GOIAS',       '1500', NULL,                    '74000100', 520870,
     '1990-07-22', 'F', 4, NULL, 10, 0, '6232110002', '62991630002', 0, '2025-12-15',
     'F20.0', 'F25.1', NULL, NULL, NULL,
     2, 1, 'S', 7018977, 4, 1, 'BPA',
     'N', 'N', 'N', 'N', 'N',
     202512000002, 'CENTRO', 82, 'paciente2@email.com', '00000000002'),
    (103, 1, 3, 52, '2025-12-01', 5604591, '700000000000003', '2025-12-01', '2025-12-31',
     'PACIENTE INFANTIL TRES',                1003, 'MARIA TESTE TRES',
     'RUA CV18',            'SN',   'RESIDENCIAL CENTER',    '74905450', 520870,
     '2012-03-08', 'M', 4, 'JOAO RESPONSAVEL', 10, 0, '6232110003', '62991630003', 0, '2025-12-15',
     'F90.0', NULL, NULL, NULL, NULL,
     1, 1, 'S', 5604591, 2, 1, 'BPA',
     'N', 'N', 'N', 'N', 'N',
     202512000003, 'CENTER VILLE', 81, NULL, '00000000003'),
    (104, 1, 4, 52, '2025-12-01', 3624969, '700000000000004', '2025-12-01', '2025-12-31',
     'PACIENTE TESTE QUATRO',                 1004, 'MARIA TESTE QUATRO',
     'RUA RB 51 A',         'SN',   'QD 51 LT 72',           '74474396', 520870,
     '1978-11-30', 'F', 2, NULL, 10, 0, '6232110004', '62991630004', 0, '2025-12-15',
     'F31.0', NULL, NULL, NULL, NULL,
     1, 1, 'N', NULL, 5, 1, 'BPA',
     'N', 'N', 'N', 'N', 'N',
     202512000004, 'RECANTO DO BOSQUE', 81, 'paciente4@email.com', '00000000004'),
    (105, 1, 5, 52, '2025-12-01', 7018977, '700000000000005', '2025-12-01', '2025-12-31',
     'PACIENTE TESTE CINCO',                  1005, 'MARIA TESTE CINCO',
     'AVENIDA GOIAS',       '1500', NULL,                    '74000100', 520870,
     '1965-02-18', 'M', 1, NULL, 10, 0, '6232110005', '62991630005', 0, '2025-12-15',
     'F10.2', 'F19.2', NULL, NULL, NULL,
     2, 2, 'S', 7018977, 6, 1, 'BPA',
     'S', 'S', 'S', 'S', 'N',
     202512000005, 'CENTRO', 82, NULL, '00000000005'),
    -- Competência 2026-01 (3 pacientes)
    (201, 2, 1, 52, '2026-01-01', 7018977, '700000000000001', '2026-01-01', '2026-01-31',
     'PACIENTE TESTE UM',                     1001, 'MARIA TESTE UM',
     'RUA C 149',           'SN',   'QD 325 LT 11',         '74230050', 520870,
     '1985-04-12', 'M', 1, NULL, 10, 0, '6232110001', '62991630001', 0, '2026-01-20',
     'F19.2', NULL, NULL, NULL, NULL,
     1, 1, 'S', 7018977, 3, 1, 'BPA',
     'N', 'S', 'S', 'N', 'N',
     202601000001, 'JARDIM AMERICA', 81, 'paciente1@email.com', '00000000001'),
    (202, 2, 2, 52, '2026-01-01', 3624969, '700000000000004', '2026-01-01', '2026-01-31',
     'PACIENTE TESTE QUATRO',                 1004, 'MARIA TESTE QUATRO',
     'RUA RB 51 A',         'SN',   'QD 51 LT 72',           '74474396', 520870,
     '1978-11-30', 'F', 2, NULL, 10, 0, '6232110004', '62991630004', 0, '2026-01-20',
     'F31.0', NULL, NULL, NULL, NULL,
     1, 1, 'N', NULL, 4, 1, 'BPA',
     'N', 'N', 'N', 'N', 'N',
     202601000002, 'RECANTO DO BOSQUE', 81, 'paciente4@email.com', '00000000004'),
    (203, 2, 3, 52, '2026-01-01', 5604591, '700000000000006', '2026-01-01', '2026-01-31',
     'PACIENTE INFANTIL SEIS',                1006, 'MARIA TESTE SEIS',
     'AVENIDA GOIAS',       '700',  NULL,                    '74000100', 520870,
     '2010-09-14', 'F', 4, 'PEDRO RESPONSAVEL', 10, 0, '6232110006', '62991630006', 0, '2026-01-20',
     'F84.0', NULL, NULL, NULL, NULL,
     1, 1, 'S', 5604591, 2, 1, 'BPA',
     'N', 'N', 'N', 'N', 'N',
     202601000003, 'CENTRO', 82, NULL, '00000000006');

-- 9.3 Itens / procedimentos de cada paciente
INSERT INTO raas_psi_item (
    cd_raas_psi_item, cd_raas_psi, linha, unidade_federacao, competencia, unidade_prestadora_servico,
    cartao_nacional_saude, dt_inicio_validade,
    cod_procedimento, cod_cbo_executante, cns_executante, dt_execucao_procedimento,
    servico, classificacao, quantidade_realizada,
    origem_informacoes, local_realizacao, cpf_paciente
) VALUES
    -- Paciente 101 — 3 procedimentos
    (1001, 101, 1, 52, '2025-12-01', 7018977, '700000000000001', '2025-12-01',
     301080232, '225125', '700000000000777', '2025-12-04', 115, 1, 1, 'BPA', 'C', '00000000001'),
    (1002, 101, 2, 52, '2025-12-01', 7018977, '700000000000001', '2025-12-01',
     301080275, '251510', '700000000000777', '2025-12-11', 115, 2, 1, 'BPA', 'C', '00000000001'),
    (1003, 101, 3, 52, '2025-12-01', 7018977, '700000000000001', '2025-12-01',
     301080283, '223505', '987654321098765', '2025-12-18', 115, 1, 1, 'BPA', 'C', '00000000001'),
    -- Paciente 102 — 4 procedimentos
    (1004, 102, 1, 52, '2025-12-01', 7018977, '700000000000002', '2025-12-01',
     301080240, '225170', '700000000000777', '2025-12-05', 115, 1, 1, 'BPA', 'C', '00000000002'),
    (1005, 102, 2, 52, '2025-12-01', 7018977, '700000000000002', '2025-12-01',
     301080232, '251510', '700000000000777', '2025-12-12', 115, 2, 1, 'BPA', 'C', '00000000002'),
    (1006, 102, 3, 52, '2025-12-01', 7018977, '700000000000002', '2025-12-01',
     301080186, '251510', '700000000000777', '2025-12-19', 115, 2, 1, 'BPA', 'C', '00000000002'),
    (1007, 102, 4, 52, '2025-12-01', 7018977, '700000000000002', '2025-12-01',
     301080275, '225170', '700000000000777', '2025-12-26', 115, 1, 1, 'BPA', 'C', '00000000002'),
    -- Paciente 103 — 2 procedimentos
    (1008, 103, 1, 52, '2025-12-01', 5604591, '700000000000003', '2025-12-01',
     301080275, '225170', '700000000000888', '2025-12-06', 115, 1, 1, 'BPA', 'C', '00000000003'),
    (1009, 103, 2, 52, '2025-12-01', 5604591, '700000000000003', '2025-12-01',
     301080186, '251510', '700000000000888', '2025-12-13', 115, 2, 1, 'BPA', 'C', '00000000003'),
    -- Paciente 104 — 5 procedimentos
    (1010, 104, 1, 52, '2025-12-01', 3624969, '700000000000004', '2025-12-01',
     301080135, '225170', '700000000000777', '2025-12-02', 115, 1, 1, 'BPA', 'C', '00000000004'),
    (1011, 104, 2, 52, '2025-12-01', 3624969, '700000000000004', '2025-12-01',
     301080186, '251510', '700000000000777', '2025-12-09', 115, 2, 1, 'BPA', 'C', '00000000004'),
    (1012, 104, 3, 52, '2025-12-01', 3624969, '700000000000004', '2025-12-01',
     301080186, '251510', '700000000000777', '2025-12-16', 115, 2, 1, 'BPA', 'C', '00000000004'),
    (1013, 104, 4, 52, '2025-12-01', 3624969, '700000000000004', '2025-12-01',
     301080283, '223505', '987654321098765', '2025-12-23', 115, 1, 1, 'BPA', 'C', '00000000004'),
    (1014, 104, 5, 52, '2025-12-01', 3624969, '700000000000004', '2025-12-01',
     301080275, '225170', '700000000000777', '2025-12-30', 115, 1, 1, 'BPA', 'C', '00000000004'),
    -- Paciente 105 — 6 procedimentos
    (1015, 105, 1, 52, '2025-12-01', 7018977, '700000000000005', '2025-12-01',
     301080232, '225125', '700000000000777', '2025-12-03', 115, 1, 1, 'BPA', 'C', '00000000005'),
    (1016, 105, 2, 52, '2025-12-01', 7018977, '700000000000005', '2025-12-01',
     301080186, '251510', '700000000000777', '2025-12-10', 115, 2, 1, 'BPA', 'C', '00000000005'),
    (1017, 105, 3, 52, '2025-12-01', 7018977, '700000000000005', '2025-12-01',
     301080186, '251510', '700000000000777', '2025-12-17', 115, 2, 1, 'BPA', 'C', '00000000005'),
    (1018, 105, 4, 52, '2025-12-01', 7018977, '700000000000005', '2025-12-01',
     301080275, '225170', '700000000000777', '2025-12-24', 115, 1, 1, 'BPA', 'C', '00000000005'),
    (1019, 105, 5, 52, '2025-12-01', 7018977, '700000000000005', '2025-12-01',
     301080240, '225170', '700000000000777', '2025-12-29', 115, 1, 1, 'BPA', 'C', '00000000005'),
    (1020, 105, 6, 52, '2025-12-01', 7018977, '700000000000005', '2025-12-01',
     301080283, '223505', '987654321098765', '2025-12-31', 115, 1, 1, 'BPA', 'C', '00000000005'),
    -- Paciente 201 — 3 procedimentos (jan/2026)
    (2001, 201, 1, 52, '2026-01-01', 7018977, '700000000000001', '2026-01-01',
     301080232, '225125', '700000000000777', '2026-01-08', 115, 1, 1, 'BPA', 'C', '00000000001'),
    (2002, 201, 2, 52, '2026-01-01', 7018977, '700000000000001', '2026-01-01',
     301080275, '251510', '700000000000777', '2026-01-15', 115, 2, 1, 'BPA', 'C', '00000000001'),
    (2003, 201, 3, 52, '2026-01-01', 7018977, '700000000000001', '2026-01-01',
     301080283, '223505', '987654321098765', '2026-01-22', 115, 1, 1, 'BPA', 'C', '00000000001'),
    -- Paciente 202 — 4 procedimentos (jan/2026)
    (2004, 202, 1, 52, '2026-01-01', 3624969, '700000000000004', '2026-01-01',
     301080135, '225170', '700000000000777', '2026-01-07', 115, 1, 1, 'BPA', 'C', '00000000004'),
    (2005, 202, 2, 52, '2026-01-01', 3624969, '700000000000004', '2026-01-01',
     301080186, '251510', '700000000000777', '2026-01-14', 115, 2, 1, 'BPA', 'C', '00000000004'),
    (2006, 202, 3, 52, '2026-01-01', 3624969, '700000000000004', '2026-01-01',
     301080275, '225170', '700000000000777', '2026-01-21', 115, 1, 1, 'BPA', 'C', '00000000004'),
    (2007, 202, 4, 52, '2026-01-01', 3624969, '700000000000004', '2026-01-01',
     301080283, '223505', '987654321098765', '2026-01-28', 115, 1, 1, 'BPA', 'C', '00000000004'),
    -- Paciente 203 — 2 procedimentos (jan/2026)
    (2008, 203, 1, 52, '2026-01-01', 5604591, '700000000000006', '2026-01-01',
     301080275, '225170', '700000000000888', '2026-01-09', 115, 1, 1, 'BPA', 'C', '00000000006'),
    (2009, 203, 2, 52, '2026-01-01', 5604591, '700000000000006', '2026-01-01',
     301080186, '251510', '700000000000888', '2026-01-23', 115, 2, 1, 'BPA', 'C', '00000000006');

-- 9.4 Arquivos RAAS já processados (para os endpoints de listar e download)
-- Cada um dos 12 CAPS tem ao menos 1 registro com total_folha > 0,
-- garantindo que apareçam em GET /v1/unidades (que faz DISTINCT em raas_processo).
INSERT INTO raas_processo
    (mes, ano, dt_geracao, empresa, nome_empresa, path, status, total_folha, texto)
VALUES
    -- Competência 12/2025
    (12, 2025, '2025-12-31',    249644, 'CAPS AD III IPE',
     'PA52123412202501.txt', '01', 3.00, '01#HEADER CAPS AD III IPE'),
    (12, 2025, '2025-12-31', 101347976, 'CAPS AD OESTE',
     'PA52123412202502.txt', '01', 4.00, '01#HEADER CAPS AD OESTE'),
    (12, 2025, '2025-12-31',    249646, 'CAPS AD3 NOROESTE',
     'PA52123412202503.txt', '01', 5.00,
     '01#FATURAMENTO SMS#GCPAH#25141524000123#SECRETARIA MUN DE SAUDE DE GOIANIA#M#20251231#1.0#202512a' || E'\n' ||
     '02#700000000000001#PACIENTE TESTE UM#19850412#M'),
    (12, 2025, '2025-12-31',  64102343, 'CAPS LIBERDADE',
     'PA52123412202504.txt', '01', 2.00, '01#HEADER CAPS LIBERDADE'),
    (12, 2025, '2025-12-31',    249922, 'CENTRO DE ATENCAO A SAUDE DE ALCOOLISTAS E TOXIC CAPSAD',
     'PA52123412202505.txt', '01', 6.00, '01#HEADER CAPSAD'),
    (12, 2025, '2025-12-31',    249924, 'CENTRO DE ATENCAO PSICOSSOCIAL AGUA VIVA',
     'PA52123412202506.txt', '01', 3.00, '01#HEADER AGUA VIVA'),
    (12, 2025, '2025-12-31',    249926, 'CENTRO DE ATENCAO PSICOSSOCIAL BEIJA FLOR',
     'PA52123412202507.txt', '01', 4.00, '01#HEADER BEIJA FLOR'),
    (12, 2025, '2025-12-31',    249928, 'CENTRO DE ATENCAO PSICOSSOCIAL ESPERANCA',
     'PA52123412202508.txt', '01', 2.00, '01#HEADER ESPERANCA'),
    (12, 2025, '2025-12-31',    249930, 'CENTRO DE ATENCAO PSICOSSOCIAL GIRASSOL',
     'PA52123412202509.txt', '01', 5.00, '01#HEADER GIRASSOL'),
    (12, 2025, '2025-12-31',    249932, 'CENTRO DE ATENCAO PSICOSSOCIAL NOVO MUNDO',
     'PA52123412202510.txt', '02', 6.00, '01#HEADER NOVO MUNDO'),
    (12, 2025, '2025-12-31',    249934, 'CENTRO DE ATENCAO PSICOSSOCIAL VIDA',
     'PA52123412202511.txt', '01', 3.00, '01#HEADER VIDA'),
    (12, 2025, '2025-12-31', 103205682, 'REDE CAPSI CATIVAR',
     'PA52123412202512.txt', '01', 4.00, '01#HEADER CAPSI CATIVAR'),
    -- Competência 01/2026 (alguns CAPS para testar filtro por mes/ano)
    ( 1, 2026, '2026-01-31',    249646, 'CAPS AD3 NOROESTE',
     'PA52123401202601.txt', '01', 3.00,
     '01#FATURAMENTO SMS#GCPAH#25141524000123#SECRETARIA MUN DE SAUDE DE GOIANIA#M#20260131#1.0#202601a'),
    ( 1, 2026, '2026-01-31',    249932, 'CENTRO DE ATENCAO PSICOSSOCIAL NOVO MUNDO',
     'PA52123401202602.txt', '01', 4.00, '01#HEADER NOVO MUNDO JAN'),
    -- registro com total_folha = 0 (NÃO deve aparecer em /v1/raas; mas mantém empresa em /v1/unidades)
    (11, 2025, '2025-11-30',    249644, 'CAPS AD III IPE',
     'PA52123411202500.txt', '01', 0.00, '01#HEADER ZERO');

-- ============================================================================
-- 10. PERMISSÕES PARA O USUÁRIO local
-- ============================================================================
GRANT USAGE  ON SCHEMA goiania_saude TO local;
GRANT CREATE ON SCHEMA goiania_saude TO local;
GRANT ALL PRIVILEGES ON ALL TABLES    IN SCHEMA goiania_saude TO local;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA goiania_saude TO local;

ALTER DEFAULT PRIVILEGES IN SCHEMA goiania_saude
    GRANT ALL ON TABLES    TO local;
ALTER DEFAULT PRIVILEGES IN SCHEMA goiania_saude
    GRANT ALL ON SEQUENCES TO local;

ALTER ROLE local IN DATABASE goiania_saude_local SET search_path = goiania_saude, public;

-- ============================================================================
-- 11. RESUMO  ✓
-- ============================================================================
\echo '=========================================================='
\echo '  BANCO LOCAL CRIADO COM SUCESSO'
\echo '  Database : goiania_saude_local'
\echo '  Usuário  : local'
\echo '  Senha    : local'
\echo '  Schema   : goiania_saude'
\echo ''
\echo '  Dados de teste:'
\echo '    - 12 empresas (CAPS de Goiania)'
\echo '    - 2 cabeçalhos RAAS (competências 12/2025 e 01/2026)'
\echo '    - 8 pacientes (raas_psi)  / 29 procedimentos (raas_psi_item)'
\echo '    - 15 arquivos processados (raas_processo)'
\echo ''
\echo '  Subir aplicação com:'
\echo '    mvn spring-boot:run -Dspring-boot.run.profiles=local'
\echo '=========================================================='

SELECT 'estado'        AS tabela, COUNT(*) AS qtd FROM goiania_saude.estado
UNION ALL SELECT 'cidade',         COUNT(*) FROM goiania_saude.cidade
UNION ALL SELECT 'empresa',        COUNT(*) FROM goiania_saude.empresa
UNION ALL SELECT 'profissional',   COUNT(*) FROM goiania_saude.profissional
UNION ALL SELECT 'raas',           COUNT(*) FROM goiania_saude.raas
UNION ALL SELECT 'raas_psi',       COUNT(*) FROM goiania_saude.raas_psi
UNION ALL SELECT 'raas_psi_item',  COUNT(*) FROM goiania_saude.raas_psi_item
UNION ALL SELECT 'raas_processo',  COUNT(*) FROM goiania_saude.raas_processo
ORDER BY tabela;
