package br.unifor.exception

class TaskAlreadyDisabledException(hashTask: String) : APIException(
    title = "Atividade desativada", //
    message = "Atividade $hashTask já está desativada.", //
    status = 404, //
)
