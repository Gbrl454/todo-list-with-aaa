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

| Método |      Rota      |         Descrição         |
| :----: | :------------: | :-----------------------: |
|  POST  | /auth/register |     Cria novo usuário     |
|  PUT   | /auth/refresh  | Atualiza token de usuário |
|  POST  |  /auth/login   |     Login do usuário      |
|  POST  |  /auth/logout  |     Logout do usuário     |
| DELETE |     /auth      |   Desabilita o usuário    |

### Atividades

| Método |    Rota    |           Descrição            |
| :----: | :--------: | :----------------------------: |
|  GET  |   /task    |         Lista atividades         |
|  GET  |   /task/{hashTask}    |         Detalha atividade         |
|  POST  |   /task    |         Cria atividade         |
|  PUT   |   /task    | Edita informações de atividade |
|  PUT   | /task/{hashTask} |  Marcar atividade como feita   |
| DELETE | /task/{hashTask} |        Remove atividade        |
