package br.unifor.utils

import br.unifor.entities.todo.User
import br.unifor.exception.APIException
import org.eclipse.microprofile.jwt.JsonWebToken

fun JsonWebToken.getLoggedUser(): User = //
    this.name?.let {
        runCatching { User.findByUsername(username = it) } //
            .getOrElse { throw APIException(message = "Usuário logado não encontrado.", status = 404) }
    } ?: throw APIException(message = "Realize login.", status = 400)

