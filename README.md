# Desafio Técnico Move Mais

Este repositório foi criado para atender o desafio técnico proposto pela empresa Move Mais.

## Como rodar o projeto

Para rodar o projeto, siga os passos abaixo:

1. Clone o repositório:

```bash
git clone https://github.com/VictorMartelli/Desafio-Tecnico-MoveMais.git
```

2. Navegue até o diretório do projeto:

```bash
cd Desafio-Tecnico-MoveMais
```

3. Execute o projeto com o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

## Tecnologias Utilizadas

- **Java 25**: Versão mais recente da linguagem Java, garantindo acesso aos últimos recursos e otimizações de performance.
- **Spring Boot 3.5.9**: Framework para criação de aplicações Java de forma rápida e simplificada, com configuração automática e um ecossistema robusto.
- **Maven**: Ferramenta de automação de compilação e gerenciamento de dependências, utilizada para estruturar o projeto e suas bibliotecas.
- **H2 Database**: Banco de dados em memória, ideal para desenvolvimento e testes, permitindo uma inicialização rápida da aplicação sem a necessidade de um banco de dados externo.
- **Flyway**: Ferramenta para versionamento de banco de dados, garantindo que as alterações no schema do banco sejam aplicadas de forma consistente e automática.
- **Lombok**: Biblioteca que ajuda a reduzir a verbosidade do código Java, automatizando a criação de getters, setters, construtores, etc.
- **JUnit 5**: Framework de testes unitários para a plataforma Java, utilizado para garantir a qualidade e o correto funcionamento das regras de negócio.

## Endpoints Principais

A API da aplicação possui os seguintes endpoints:

- `POST /api/produtos`

  - **Descrição**: Cadastra um novo produto no sistema.
  - **Regra de Negócio**: O `SKU` do produto deve ser único. O sistema validará e retornará um erro caso o SKU já exista.

- `GET /api/produtos`

  - **Descrição**: Lista todos os produtos cadastrados.
  - **Parâmetro de Query**: `ativo` (boolean) - Permite filtrar a lista para retornar apenas produtos ativos (`true`) ou inativos (`false`).

- `GET /api/produtos/{id}/saldo`

  - **Descrição**: Consulta o saldo de estoque atual para um produto específico.
  - **Parâmetros de Caminho**: `id` do produto.

- `POST /api/movimentacoes`

  - **Descrição**: Registra uma movimentação de estoque (entrada ou saída).
  - **Corpo da Requisição**: JSON com os detalhes da movimentação (ID do produto, quantidade, tipo).
  - **Regra de Negócio**: Movimentações de `saída` não podem resultar em um estoque negativo. Caso o saldo seja insuficiente, a API retornará um erro de negócio informando a falha.

- `GET /api/produtos/{id}/movimentacoes`
  - **Descrição**: Lista todas as movimentações de estoque para um produto específico.
  - **Parâmetros de Caminho**: `id` do produto.

## Como Rodar os Testes

Para executar os testes unitários do projeto, utilize o seguinte comando na raiz do projeto:

```bash
./mvnw test
```

Este comando irá compilar o código de teste e executar todos os testes unitários da aplicação, garantindo que as regras de negócio e funcionalidades estão operando como esperado.

## Observabilidade e Monitoramento (Actuator)

Implementação do **Spring Boot Actuator** para expor métricas vitais da aplicação:

- **Health Check**: Endpoint `/actuator/health` que valida a saúde da aplicação, do disco e a conectividade com o banco de dados H2.
- **Métricas**: Base preparada para integração com ferramentas de telemetria (Prometheus/Grafana) via endpoint `/actuator/metrics`.

## Qualidade de Software

O projeto utiliza **JUnit 5** para garantir a integridade das regras de negócio:

- **Teste Unitário de Domínio**: Validamos que a entidade `Produto` impede movimentações que resultariam em saldo negativo, lançando `IllegalStateException`.
- **Execução dos Testes**:
  ```bash
  ./mvnw test
  ```
