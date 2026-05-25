# PRD — goiania-saude-raas (ambiente local)

## Problema original
> "analise toda aplicação. quero um data.sql que crie um banco de dados para testes locais. Não quero usar o H2, use o postgresql para criar um banco local de testes. no script data.sql crie o banco de dados com o nome goiania_saude_local, usuario local e senha local. preciso que insira dados nas tabelas de modo a possibilitar os testes."

## Arquitetura
- Spring Boot 3.3 + Java 21 + PostgreSQL 15
- JPA com `default_schema: goiania_saude`
- Endpoints REST:
  - `GET /api/v1/unidades` — lista unidades CAPS
  - `GET /api/v1/raas` — lista arquivos RAAS processados (paginado)
  - `GET /api/v1/raas/{id}` — download de arquivo
  - `GET /api/v1/raas/psicossocial/gerar/{mes}/{ano}` — gera RAAS PSI

## Entregue (2026-01)
- `src/main/resources/data.sql` reescrito como **script standalone do psql** que:
  - Cria role `local` com senha `local`
  - Cria database `goiania_saude_local`
  - Cria schema `goiania_saude` (alinhado ao `default_schema` da aplicação)
  - Cria **todas as tabelas legadas** (estado, cidade, raca, empresa, atendimento, vac_aplicacao, etc.)
  - Cria as **tabelas usadas pela aplicação RAAS** com schema correto:
    - `raas` (cabeçalho), `raas_psi` (paciente — 47 colunas, alinhada à `RaasPsiEntity`),
      `raas_psi_item` (procedimentos), `raas_processo` (arquivos gerados)
  - Insere dados de teste mínimos: 5 unidades CAPS, 2 cabeçalhos RAAS,
    8 pacientes / 29 procedimentos, 7 arquivos processados
  - Garante permissões e `search_path` para o usuário `local`
  - É **idempotente** — pode ser re-executado
- `src/main/resources/application-local.yml` — profile `local` apontando para
  `jdbc:postgresql://localhost:5432/goiania_saude_local` (user/senha `local`)

## Como executar
```bash
# 1) Criar/recriar o banco e os dados
psql -U postgres -h localhost -f src/main/resources/data.sql

# 2) Subir a aplicação no profile local
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Validação executada
Todas as queries reais dos repositórios foram executadas com sucesso:
- `ListarUnidadesRepository` → 5 unidades retornadas
- `ListarArquivosRaasRepository` → 6 arquivos com total_folha > 0
- `DownloadArquivosRaasRepository` → 1 arquivo recuperado pelo id
- `RaasPsiRepository.buscarHeaderPorCompetencia('2025-12-01')` → 1 header
- `RaasPsiRepository.buscarPacientesPorCompetencia('2025-12-01')` → 5 pacientes
- `RaasPsiRepository.buscarItensPorCompetencia('2025-12-01')` → 20 itens

## Backlog / Próximas ações
- (P1) Ajustar entidade `ArquivosRaasEntity` — campo `codigoEmpresa` está tipado
  como `String` mas a coluna `raas_processo.empresa` precisa ser `BIGINT` para o
  `JOIN empresa e ON e.empresa = r.empresa` funcionar (foi modelada como BIGINT).
- (P2) Adicionar Flyway/Liquibase para versionamento de schema.
- (P2) Criar volume maior de dados (500+ registros) para testes de paginação/performance.
