package br.unifor.exception

import br.unifor.exception.model.FieldError

class FieldException(
    val fieldErrors: List<FieldError>, //
) : APIException(
    title = "Erro ao preencher campos", //
    message = "Corrija os problemas listados...", //
    status = 400, //
)
