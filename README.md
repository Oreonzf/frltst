![Logo do Projeto](https://camo.githubusercontent.com/0cedb1df1861b796963b6b050f47ec81a0fbfe8f06ef4892692a05cc77c4ac7a/68747470733a2f2f736974652e666f726c6576656e2e636f6d2f696d672f6c6f676f7469706f5f677265656e2e706e67)

# API de Cadastro de Estudantes

## Descrição

A **API de Cadastro de Estudantes** é uma aplicação desenvolvida em Java utilizando Spring Boot para gerenciar o cadastro de estudantes, permitindo operações de CRUD (Create, Read, Update, Delete). A API oferece endpoints para criação, consulta, atualização e deleção de estudantes, com suporte para operações parciais (PATCH).

## Objetivo

A criação desta API tem como objetivo fornecer um serviço RESTful robusto e escalável para gerenciar informações de estudantes. Ela pode ser utilizada em sistemas acadêmicos, portais de administração escolar, ou qualquer aplicação que necessite de um cadastro e gerenciamento de estudantes.

## Funcionalidades

A API permite:

- **Registrar estudantes:** Armazenar informações básicas como nome, sobrenome, matrícula e telefones.
- **Consultar estudantes:** Recuperar a lista completa de estudantes cadastrados ou buscar por ID.
- **Atualizar estudantes:** Modificar informações de um estudante específico, seja de forma completa ou parcial.
- **Deletar estudantes:** Remover um estudante do cadastro.

### O deploy projeto está localizado no Servidor AWS IP: http://18.227.81.223:8080/students
### A documentação API do projeto se encontra em http://18.227.81.223:8080/swagger-ui/index.html

## Tecnologias Utilizadas

- **Java 17:** A linguagem de programação utilizada para o desenvolvimento da API.
- **Spring Boot 3.3.2:** Framework que facilita a criação de aplicações stand-alone de alta qualidade.
- **Maven:** Gerenciador de dependências e automação de build.
- **JPA / Hibernate:** Framework de mapeamento objeto-relacional (ORM) para persistência de dados no banco MySQL.
- **MySQL:** Banco de dados relacional utilizado para armazenar os registros dos estudantes.
- **Swagger:** Ferramenta para documentação e teste da API.
- **JUnit 5:** Framework de testes utilizado para garantir a qualidade do código.
- **Mockito:** Framework para criação de testes unitários utilizando mocks.

## Estrutura do Projeto

O projeto está estruturado seguindo as boas práticas de desenvolvimento em camadas:

- **Controller:** Contém os endpoints da API e lida com as requisições HTTP.
- **Service:** Contém a lógica de negócios da aplicação, fazendo a intermediação entre o Controller e o Repository.
- **Repository:** Interface que lida diretamente com o banco de dados, utilizando o Spring Data JPA.
- **Model:** Define as entidades que são persistidas no banco de dados.
- **DTOs (Data Transfer Objects):** Utilizados para transferir dados entre as camadas da aplicação.
- **Tests:** Inclui testes unitários para garantir o correto funcionamento das funcionalidades.
- **Resources**: Contém o arquivo Swagger.

## Requisitos

- **Java 17** ou superior
- **Maven 3.6.1** ou superior
- **MySQL** (ou outro banco de dados relacional)

# Funcionalidades

## Configuração

### Todas as configurações podem ser alteradas pelo **application.properties**

## Endpoints
### Criar Estudante
- **Matrículas não podem se repetir**
- **Mais ou menos números de telefones podem ser configurados**
  
POST 
```java
/students/register
(exemplo)
Request Body:
json
Copiar código
{
  "nome": "João",
  "sobrenome": "Silva",
  "matricula": "123456",
  "telefones": ["1234-5678", "9876-5432"]
}
```
### Consultar Todos os Estudantes
GET
```
/students
```
### Consultar Estudante por ID
GET
```java
/students/{id}
```
### Atualizar Estudante
PUT 
```java
/students/{id}
(exemplo)
Request Body:
json
Copiar código
{
  "nome": "João",
  "sobrenome": "Silva",
  "matricula": "654321",
  "telefones": ["1234-5678", "8765-4321"]
}
```

### Atualizar Parcialmente Estudante
PATCH
```java
/students/{id}
(exemplo)
Request Body (exemplo):
json
Copiar código
{
  "sobrenome": "Pereira"
}
```
### Deletar Estudante
DELETE
```java
/students/delete/{id}
```
## Testes

### Para rodar os testes unitários, utilize o Maven:
```cmd
mvn test
```
