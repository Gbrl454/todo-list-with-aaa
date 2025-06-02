package br.unifor.exception

class TaskInformationHasChangedException(hashTask: String) : APIException(
    title = "Atividade sem alterações", //
    message = "Nenhuma informação da atividade $hashTask foi alterada.", //
    status = 422, //
)
