# goiania-saude-raas

Este projeto é um backend Java 21 com Spring Boot 3.x para a gestão e consulta de arquivos RAAS (Registro de Ações Ambulatoriais de Saúde) da SMS Goiânia, seguindo arquitetura hexagonal e boas práticas de logging, documentação e análise estática.

## Funcionalidades

- **Consulta de Arquivos RAAS**: Endpoint para listar arquivos RAAS filtrando por mês, ano, código da empresa e situação.
- **Query Nativa Otimizada**: Consulta eficiente ao banco de dados, com mapeamento correto dos campos para a entidade JPA.
- **Logging Global**: Logs automáticos em todas as camadas (UseCase, Adapter, Repository, SQL) com Correlation ID, sem poluir o código-fonte, via AspectJ e configuração do Hibernate.
- **Documentação OpenAPI (Swagger)**: Interface personalizada centraliza toda a documentação, mantendo o controller limpo. Swagger UI disponível para testes e exploração da API.
- **Análise Estática**: Código validado por PMD e Checkstyle, com regras de guardas explícitas para logs.

## Estrutura do Projeto

- `src/main/java/br/gov/goiania/saude/raas/`
  - `application/`: Casos de uso, DTOs, portas.
  - `domain/`: Modelos de domínio e serviços.
  - `infrastructure/`
    - `adapter/`: Adapters de entrada/saída (ex: web, banco).
    - `config/`: Configurações globais (ex: LoggingAspect, OpenApiConfig).
    - `web/swagger/`: Interface de documentação OpenAPI.
- `src/main/resources/application.yml`: Configuração de logs, datasource, etc.
- `pom.xml`: Dependências e plugins Maven.

## Como Executar

1. **Pré-requisitos**:
   - Java 21
   - Maven 3.8+
   - Banco de dados configurado em `application.yml`

2. **Build do Projeto**:
   ```shell
   mvn clean verify
   ```

3. **Executar a Aplicação**:
   ```shell
   mvn spring-boot:run
   ```
   Ou rode o JAR gerado em `target/`:
   ```shell
   java -jar target/raas-1.0.0-SNAPSHOT.jar
   ```

4. **Acessar a API**:
   - Endpoints disponíveis sob o contexto `/api`.
   - Exemplo de endpoint principal:
     - `GET /api/v1/raas?mes=5&ano=2026&codigoEmpresa=123&situacao=GERADO`

5. **Swagger UI**:
   - Acesse a documentação interativa em: [http://http://localhost:8081/api/swagger-ui/index.html](http://http://localhost:8081/api/swagger-ui/index.html)

## Logging

- Todas as queries SQL e parâmetros são logados automaticamente.
- Métodos de UseCase, Adapter e Repository são logados via AspectJ.
- O Correlation ID é propagado via MDC para rastreabilidade entre requisições.
- Configuração de logs pode ser ajustada em `application.yml`:
  ```yaml
  logging:
    level:
      org.hibernate.SQL: DEBUG
      org.hibernate.orm.jdbc.bind: TRACE
  ```

## Documentação OpenAPI

- Toda a documentação reside na interface `ListarArquivosRaasSwagger` (`infrastructure.adapter.web.swagger`).
- O controller implementa esta interface, mantendo-se limpo de anotações de documentação.
- DTOs de resposta são anotados com `@Schema` para descrição dos campos.
- Configuração global em `OpenApiConfig`.

## Análise Estática

- O projeto utiliza PMD e Checkstyle. Para validar, execute:
  ```shell
  mvn pmd:check checkstyle:check
  ```
- Todos os logs possuem guardas explícitas para evitar violações.

## Testes

- Testes unitários podem ser executados com:
  ```shell
  mvn test
  ```

## Suporte

Para dúvidas ou sugestões, abra uma issue ou entre em contato com a equipe de desenvolvimento da SMS Goiânia.

## Layout da Linha 15 — Registro Detalhe: Folha de Dados do Paciente (Atenção Psicossocial)

Este registro traz os dados demográficos, residenciais e clínicos do paciente em acompanhamento psicossocial.

### Observação importante sobre consistência dos dados

O sistema exporta os dados **exatamente como estão cadastrados no banco de dados**, aplicando apenas as formatações exigidas pelo layout oficial (padding, zeros à esquerda, espaços à direita, etc.).

> **Divergências entre `total_procedimentos` e a contagem real de itens** são provenientes da base de dados e devem ser corrigidas diretamente nas tabelas `raas_psi` e `raas_psi_item`.

### Estrutura da Linha 15

| Seq | Posição | Tam | Nome do Campo | Descrição | Preenchimento / Regra |
|-----|---------|-----|---------------|-----------|----------------------|
| 1 | 001-002 | 2 | ras_codlinha | Código da linha | Fixo "15" |
| 2 | 003-004 | 2 | ras_coduf | Código IBGE da UF | Numérico, zeros à esquerda |
| 3 | 005-010 | 6 | ras_cmp | Competência | Formato AAAAMM |
| 4 | 011-017 | 7 | ras_codcnes | CNES da Unidade Prestadora | Numérico, com dígito verificador |
| 5 | 018-032 | 15 | ras_cnspct | CNS do Paciente | **Regra de exclusividade**: Se CPF preenchido, CNS = zeros |
| 6 | 033-040 | 8 | ras_dtiinval | Data inicial da validade | Formato YYYYMMDD |
| 7 | 041-048 | 8 | ras_dtfimval | Data final da validade | Formato YYYYMMDD. Se vazio, espaços |
| 8 | 049-078 | 30 | ras_nomepcnte | Nome do paciente | Texto, espaços à direita |
| 9 | 079-088 | 10 | ras_npront | Número do Prontuário | Texto, espaços à direita |
| 10 | 089-118 | 30 | ras_nomemae | Nome da mãe | Texto, espaços à direita |
| 11 | 119-148 | 30 | ras_logpcnte | Logradouro | Texto, espaços à direita |
| 12 | 149-153 | 5 | ras_numpcnte | Número da residência | Texto, espaços à direita |
| 13 | 154-163 | 10 | ras_cplpcnte | Complemento | Texto, espaços à direita |
| 14 | 164-171 | 8 | ras_ceppcnte | CEP | Texto, espaços à direita |
| 15 | 172-178 | 7 | ras_munpcnte | Município IBGE com DV | Texto. Sem DV, espaço na última posição |
| 16 | 179-186 | 8 | ras_datanascim | Data de nascimento | Formato YYYYMMDD |
| 17 | 187 | 1 | ras_sexopcnte | Sexo | M ou F |
| 18 | 188-189 | 2 | ras_raca | Raça/Cor | 01-Branca; 02-Preta; 03-Parda; 04-Amarela; 05-Indígena |
| 19 | 190-219 | 30 | ras_nomeresp | Nome do responsável | Texto, espaços à direita |
| 20 | 220-222 | 3 | ras_nascpcnte | Nacionalidade | Código Anexo VIII PT/MS/SAS 205/96 |
| 21 | 223-226 | 4 | ras_etnia | Etnia | Obrigatório se Raça = 05 |
| 22 | 227-237 | 11 | ras_telefone | Telefone | Texto, espaços à direita |
| 23 | 238-248 | 11 | ras_celular | Celular | Texto, espaços à direita |
| 24 | 249-250 | 2 | ras_motsaida | Motivo de Saída/Permanência | Portaria 719/2007 |
| 25 | 251-258 | 8 | ras_dtobitoalta | Data da ocorrência | Formato AAMMDD. Se vazio, espaços |
| 26 | 259-262 | 4 | pap_cidp | CID Principal | Texto, espaços à direita |
| 27 | 263-266 | 4 | pap_cids1 | CID Secundário 1 | Texto, espaços à direita |
| 28 | 267-270 | 4 | pap_cids2 | CID Secundário 2 | Texto, espaços à direita |
| 29 | 271-274 | 4 | pap_cids3 | CID Secundário 3 | Texto, espaços à direita |
| 30 | 275-278 | 4 | ras_cidca | CID Causas Associadas | Texto, espaços à direita |
| 31 | 279-280 | 2 | ras_CARATE | Caráter do atendimento | 01-Eletivo; 02-Urgência; 03/04-Acidente Trabalho; 05-Trânsito; 06-Lesões/Envenenamentos |
| 32 | 281-282 | 2 | ras_origempcn | Origem do Paciente | 01-Demanda Espontânea; 02-Atenção Básica; 03-Urgência; 04-Outro CAPS; 05-Hospital Dia; 06-Hosp. Psiquiátrico |
| 33 | 283 | 1 | ras_cobertura_ESF | Cobertura ESF? | S = SIM; N = NÃO |
| 34 | 284-290 | 7 | ras_cnes_esf | CNES da cobertura ESF | **Regra**: Preencher apenas se Cobertura ESF = S. Se N, espaços |
| 35 | 291-295 | 5 | ras_total_acoes | Total de Ações nesta folha | Numérico, zeros à esquerda. **Valor vem do banco** |
| 36 | 296-297 | 2 | ras_dest_paciente | Destino do paciente | 00-Permanência; 01-Outro CAPS; 02-Atenção Básica; 03-Alta; 04-Óbito |
| 37 | 298-300 | 3 | ras_org | Origem das Informações | "RAS" = SIA/SUS; "EXT" = Outros Sistemas |
| 38 | 301 | 1 | ras_situacao_rua | Situação de rua? | S = SIM; N = NÃO |
| 39 | 302 | 1 | ras_usu_droga | Usuário de drogas? | S = SIM; N = NÃO |
| 40 | 303-305 | 3 | ras_usu_tipo_droga | Tipos de drogas | A = Álcool; C = Crack; O = Outros. Obrigatório se Linha 39 = S |
| 41 | 306-318 | 13 | ras_autorizacao | Número da autorização | Texto, espaços à direita |
| 42 | 319-348 | 30 | ras_bairro | Bairro | Texto, espaços à direita |
| 43 | 349-351 | 3 | ras_cod_logradouro | Código do Logradouro | Texto, espaços à direita |
| 44 | 352-391 | 40 | ras_email | E-mail | Texto, espaços à direita |
| 45 | 392-402 | 11 | ras_cpfpct | CPF do Paciente | **Regra de exclusividade**: Se CNS preenchido, CPF = zeros |
| 46 | 403-406 | 4 | ras_filler | Espaço reservado | Espaços em branco |
| 47 | 407-408 | 2 | ras_fim | Final da linha | CRLF (\r\n) |

### Regras de Exclusividade CNS vs CPF

- **Se CNS preenchido e CPF vazio**: CNS é exportado, CPF = zeros
- **Se CPF preenchido e CNS vazio**: CPF é exportado, CNS = zeros
- **Se ambos preenchidos**: CNS = zeros, CPF é exportado (prioridade ao CPF segundo layout oficial)

### Regras Condicionais

- **Etnia (223-226)**: Obrigatório apenas se Raça/Cor = 05 (Indígena)
- **CNES ESF (284-290)**: Preencher apenas se Cobertura ESF = S. Se N, deve conter espaços
- **Tipo de Droga (303-305)**: Obrigatório apenas se Usuário de Drogas = S
- **Data de Ocorrência (251-258)**: Preencher apenas em caso de alta, transferência ou óbito

## Layout da Linha 01 (Header RAAS)

| Posição   | Tamanho | Nome do Campo (Layout) | Descrição                                 | Tipo / Preenchimento            | Exemplo                             |
|-----------|---------|------------------------|--------------------------------------------|---------------------------------|-------------------------------------|
| 001-002   | 002     | cbc-codlinha           | Código da linha                            | Fixo                            | 01                                  |
| 003-007   | 005     | cbc-hdr                | Identificador do arquivo                   | Fixo                            | #RAS#                               |
| 008-013   | 006     | cbc-mvm                | Competência (Ano + Mês)                    | AAAAMM                          | 202510                              |
| 014-019   | 006     | cbc-lin                | Quantidade de folhas (nº de registros 15)  | Numérico, zeros à esquerda      | 001839                              |
| 020-023   | 004     | cbc-smt-vrf            | Campo de Controle (Checksum)               | Numérico (1111 a 2221)          | 1305                                |
| 024-053   | 030     | cbc-rsp                | Nome do órgão responsável (Origem)         | Texto, espaços à direita        | FATURAMENTO SMS GCPAH               |
| 054-059   | 006     | cbc-sgl                | Sigla do órgão responsável                 | Texto, espaços à direita        | 251415                              |
| 060-073   | 014     | cbc-cgccpf             | CNPJ do órgão responsável                  | Numérico, zeros à esquerda      | 25141524000123                      |
| 074-113   | 040     | cbc-dst                | Nome do órgão de destino                   | Texto, espaços à direita        | SECRETARIA MUN DE SAUDE DE GOIANIA  |
| 114       | 001     | cbc-dst-in             | Indicador do destino                       | M (Municipal) ou E (Estadual)   | M                                   |
| 115-122   | 008     | cbc-dtger              | Data de geração do arquivo                 | AAAAMMDD                        | 20251031                            |
| 123-127   | 005     | cbc_versao             | Versão do sistema                          | XX.XX                           | 02.21                               |
| 128-137   | 010     | Filler                 | Campo reservado                            | Zeros ou espaços                | 0000000000                          |
| 138-144   | 007     | cbc_bdversao           | Versão do BDSIA utilizado                  | Ex: 202510a                     | 202510a                             |
| 145-159   | 015     | cbc-fim                | Final da linha                             | Espaços + \r\n                  |                                     |
