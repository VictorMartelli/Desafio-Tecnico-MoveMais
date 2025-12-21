## 1. Diagrama de Contexto (C4 - Nível 1)

Este diagrama descreve o sistema de estoque no ecossistema da MoveMais e como o Operador interage com ele.

```mermaid
C4Context
    title Context Diagram - Sistema de Estoque MoveMais

    Person(operator, "Operador de Estoque", "Usuário responsável por movimentações e consultas de saldo.")
    System(inventory_system, "Sistema de Estoque", "Gerencia o ciclo de vida dos produtos, saldos e auditoria de movimentações.")

    Rel(operator, inventory_system, "Realiza entradas, saídas e consultas", "HTTPS/JSON")
```

## 2. Diagrama de Containers (C4 - Nível 2)

O sistema é construído como um Monolito Modular seguindo Clean Architecture, garantindo desacoplamento entre a lógica de negócio e a infraestrutura.

```mermaid
C4Container
    title Container Diagram - Sistema de Estoque MoveMais

    Person(operator, "Operador de Estoque", "Gerencia o estoque")

    System_Boundary(c1, "Aplicação MoveMais") {
        Container(web_api, "API Controllers", "Java / Spring Boot", "Endpoints REST para Produtos e Movimentações.")
        Container(domain_logic, "Core Domain & Use Cases", "Java", "Regras de negócio (Saldos, Validações) e Casos de Uso.")
        Container(persistence, "Persistence Adapter", "Spring Data JPA", "Mapeamento entre objetos de domínio e banco de dados.")
    }

    ContainerDb(database, "Banco de Dados", "H2 (Development)", "Armazena Produtos, Entidades e Histórico.")

    Rel(operator, web_api, "Requisições REST", "JSON/HTTPS")
    Rel(web_api, domain_logic, "Chama Casos de Uso", "In-process")
    Rel(domain_logic, persistence, "Persiste/Recupera dados", "Repository Interface")
    Rel(persistence, database, "SQL Queries", "JDBC")
```
