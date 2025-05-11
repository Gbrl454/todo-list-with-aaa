package br.unifor.exception

class UserNotFoundException(username:String) : APIException(
    title = "Usuário não encontrado", //
    message = "Usuário $username não encontrado.", //
    status = 404, //
)
