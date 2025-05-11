package br.unifor.exception

class RegisterKcException : APIException(
    title = "Falha ao registar usuário", //
    message = "Ocorreu um erro ao cadastrar usuário.", //
    status = 400, //
)
