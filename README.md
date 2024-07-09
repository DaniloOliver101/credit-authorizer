# Credit Authorizer

## Descrição

Este projeto é uma aplicação de autorização de crédito desenvolvida em Kotlin usando Spring Boot e Java 17.

## Tecnologias Utilizadas

- Kotlin
- Spring Boot
- Java 17
- Gradle
- JPA (Hibernate)
- Flyway
- OpenFeign
- H2 Database (para desenvolvimento e testes)
- Swagger (para documentação da API)

## Estrutura do Projeto

- **Controller:** Define os endpoints da API.
- **Service:** Contém a lógica de negócios da aplicação.
- **Model:** Define as entidades JPA.
- **Repository:** Interage com o banco de dados.
- **Config:** Configurações da aplicação, incluindo o Swagger.

## Executando o Projeto Localmente

### Pré-requisitos

- Java 17 instalado
- Gradle instalado
- Docker instalado (opcional para execução via Docker)

### Executando com Gradle

1. Clone o repositório:

    ```sh
    git clone https://github.com/DaniloOliver101/credit-authorizer.git
    cd credit-authorizer
    ```

2. Construa o projeto:

    ```sh
    ./gradlew build
    ```

3. Execute a aplicação:

    ```sh
    ./gradlew bootRun
    ```

### Executando com Docker

1. Clone o repositório:

    ```sh
    git clone https://github.com/DaniloOliver101/credit-authorizer.git
    cd credit-authorizer
    ```

2. Construa a imagem Docker:

    ```sh
    docker build -t credit-authorizer .
    ```

3. Execute o contêiner Docker:

    ```sh
    docker run -p 8080:8080 credit-authorizer
    ```

## Documentação da API

A documentação da API está disponível via Swagger. Após iniciar a aplicação, acesse:

