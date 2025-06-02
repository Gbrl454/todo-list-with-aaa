package br.unifor.exception

class TryingLoginException : APIException(
    title = "Falha no logar", //
    message = "Erro ao tentar realizar login.", //
    status = 400, //
)
