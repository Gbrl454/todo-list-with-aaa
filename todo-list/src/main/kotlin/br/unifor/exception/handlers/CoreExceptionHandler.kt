package br.unifor.exception.handlers

import br.unifor.core.exception.CoreException
import br.unifor.exception.APIException
import br.unifor.extensions.makeDefaultResponse
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class CoreExceptionHandler(
    @Context private val uriInfo: UriInfo
) : ExceptionMapper<CoreException> {
    override fun toResponse(exception: CoreException?): Response = exception?.let {
        APIException(
            message = it.message ?: "Argumento inválido", //
            status = 400, //
        )
    }?.makeDefaultResponse(uriInfo = uriInfo) //
        ?: run { Response.status(Response.Status.INTERNAL_SERVER_ERROR).build() }
}
