# App Car Dealership - Microsserviço de Gerenciamento de Veículos

## Sobre o Projeto

O **App Car Dealership** é um microsserviço desenvolvido para concessionárias de carros, responsável pelo gerenciamento completo do catálogo de veículos. Ele concentra as funcionalidades de cadastro, consulta, atualização e controle de status de carros disponíveis para venda.

Este serviço faz parte de uma arquitetura de microsserviços, comunicando-se com o serviço de vendas através de APIs REST para garantir a sincronização de dados entre diferentes domínios do negócio.

### link para o vídeo com a demonstração do projeto: https://youtu.be/WadUN7Y8f_o

## 🛠️ Tecnologias Utilizadas

### Core
- **Java 21** - Linguagem de programação
- **Quarkus 3.26.3** - Framework supersônico e subatômico Java
- **Jakarta EE** - Especificação enterprise para Java
- **Maven** - Gerenciamento de dependências e build

### Persistência
- **Hibernate ORM com Panache** - Simplificação do ORM através do padrão Active Record
- **MySQL** - Banco de dados relacional
- **H2 Database** - Banco em memória para testes

### APIs e Comunicação
- **Quarkus REST** - Implementação moderna de Jakarta REST
- **Jackson** - Serialização/deserialização JSON
- **REST Client** - Cliente HTTP para comunicação entre microsserviços

### Infraestrutura e Deploy
- **Docker** - Containerização da aplicação
- **Amazon ECS** - Orquestração de containers na AWS
- **Amazon ECR** - Registro de imagens Docker
- **Amazon RDS** - Banco de dados MySQL gerenciado
- **GitHub Actions** - CI/CD pipeline

### Qualidade e Testes
- **JUnit 5** - Framework de testes
- **Mockito** - Mock de dependências
- **REST Assured** - Testes de APIs REST
- **JaCoCo** - Cobertura de código

## 💻 Como Executar Localmente

Com o docker rodando, basta executar o comando abaixo:

`quarkus dev`

o banco de dados será criado automaticamente pelo quarkus utilizando o docker.

A aplicação estará disponível em: http://localhost:8080

Swagger UI: http://localhost:8080/q/swagger-ui/

## Como Executar os Testes

execute o comando abaixo:

`mvn clean verify`

para visualizar os resultados dos testes, abra o arquivo `target/coverage/index.html` no seu navegador.

## Cobertura de Código

<img width="1552" height="324" alt="image" src="https://github.com/user-attachments/assets/deb0f749-b9f3-4874-af4b-d760bfa01c6e" />

