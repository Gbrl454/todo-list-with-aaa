package br.unifor.exception

class CloseSessionException : APIException(
    title = "Sessão do usuário", //
    message = "Erro ao encerrar sessão.", //
    status = 400, //
)
