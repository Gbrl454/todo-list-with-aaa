package br.unifor.exception.handlers

import br.unifor.exception.APIException
import br.unifor.extensions.makeResponse
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class APIExceptionHandler(
    @Context private val uriInfo: UriInfo, //
) : ExceptionMapper<APIException> {
    override fun toResponse(exception: APIException?): Response = //
        exception?.makeResponse(uriInfo = uriInfo) //
            ?: run { Response.status(Response.Status.INTERNAL_SERVER_ERROR).build() }
}
