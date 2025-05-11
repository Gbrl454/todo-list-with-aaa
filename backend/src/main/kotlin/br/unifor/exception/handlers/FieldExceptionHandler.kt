package br.unifor.exception.handlers

import br.unifor.exception.APIException
import br.unifor.exception.FieldException
import br.unifor.extensions.makeResponse
import br.unifor.exception.model.FieldError
import br.unifor.exception.model.FieldExceptionResponse
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class FieldExceptionHandler(
    @Context private val uriInfo: UriInfo, //
) : ExceptionMapper<FieldException> {
    override fun toResponse(exception: FieldException?): Response = exception //
        ?.let {
            it.fieldErrors to APIException(
                title = it.title, //
                message = it.message, //
                status = it.status, //
            )
        }?.let { (fieldErrors: List<FieldError>, exception: APIException) ->
            exception.makeResponse(uriInfo = uriInfo, response = FieldExceptionResponse(fieldErrors = fieldErrors))
        } ?: run { Response.status(Response.Status.INTERNAL_SERVER_ERROR).build() }
}