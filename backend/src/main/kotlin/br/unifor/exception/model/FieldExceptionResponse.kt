package br.unifor.exception.model

import kotlinx.serialization.Serializable

@Serializable
data class FieldExceptionResponse(
    val fieldErrors: List<FieldError>, //
) : ExceptionResponse()
