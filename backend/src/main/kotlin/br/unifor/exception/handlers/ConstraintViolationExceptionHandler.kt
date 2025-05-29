package br.unifor.exception.handlers

import br.unifor.exception.APIException
import br.unifor.exception.model.FieldError
import br.unifor.exception.model.FieldExceptionResponse
import br.unifor.extensions.makeResponse
import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class ConstraintViolationExceptionHandler(
    @Context private val uriInfo: UriInfo, //
) : ExceptionMapper<ConstraintViolationException> {
    override fun toResponse(exception: ConstraintViolationException?): Response = exception?.let {
        APIException(
            title = "Erro ao enviar dados", //
            message = "Corrija os problemas listados...", //
            status = 400, //
        )
    }?.makeResponse(
        uriInfo = uriInfo, //
        response = FieldExceptionResponse(fieldErrors = exception.constraintViolations.map { FieldError(it) }), //
    ) ?: run { Response.status(Response.Status.INTERNAL_SERVER_ERROR).build() }
}
