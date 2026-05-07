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

