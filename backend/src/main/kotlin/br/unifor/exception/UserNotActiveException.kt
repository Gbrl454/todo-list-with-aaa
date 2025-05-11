package br.unifor.exception

class UserNotActiveException(username: String) : APIException(
    title = "Usuário não ativo", //
    message = "O usuário $username foi desativado.", //
    status = 400, //
)
