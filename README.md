# TODO List

Atividade avaliativa - Projeto de Ciberproteção - Aplicação WEB de TODO List com Keycloak

## Como executar

### Requisitos

- Java 21
- Gradle
- Docker
- Node

## API - Endpoints

### Usuários

| Método |     Rota     |         Descrição         |
|:------:|:------------:|:-------------------------:|
|  POST  |    /user     |     Cria novo usuário     |
|  PUT   |    /user     | Atualiza token de usuário |
|  POST  | /user/login  |     Login do usuário      |
|  POST  | /user/logout |     Logout do usuário     |

### Atividades

| Método |    Rota    |           Descrição            |
|:------:|:----------:|:------------------------------:|
|  POST  |   /task    |         Cria atividade         |
|  PUT   |   /task    | Edita informações de atividade |
|  PUT   | /task/{id} |  Marcar atividade como feita   |
| DELETE | /task/{id} |        Remove atividade        |
