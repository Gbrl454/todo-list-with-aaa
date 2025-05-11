package br.unifor.exception.handlers

import br.unifor.exception.APIException
import br.unifor.extensions.makeResponse
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class IllegalArgumentExceptionHandler(
    @Context private val uriInfo: UriInfo, //
) : ExceptionMapper<IllegalArgumentException> {
    override fun toResponse(exception: IllegalArgumentException?): Response = exception?.let {
        APIException(
            message = "Argumento inválido. ${it.message}", //
            status = 400, //
        )
    }?.makeResponse(uriInfo = uriInfo) //
        ?: run { Response.status(Response.Status.INTERNAL_SERVER_ERROR).build() }
}
