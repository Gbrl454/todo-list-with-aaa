package br.unifor.exception

class TaskNotOwnedLoggedUserException(hashTask: String) : APIException(
    title = "Atividade de outro usuário", //
    message = "Atividade $hashTask pertence a outro usuário, você não pode acessar ela.", //
    status = 400, //
)
