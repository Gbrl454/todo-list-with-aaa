package br.unifor.exception

class TaskNotFoundException(hashTask: String) : APIException(
    title = "Atividade não encontrada", //
    message = "Atividade $hashTask não encontrada.", //
    status = 404, //
)
