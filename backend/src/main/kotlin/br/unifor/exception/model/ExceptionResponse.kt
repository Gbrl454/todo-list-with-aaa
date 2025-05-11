package br.unifor.exception.model

import kotlinx.serialization.Serializable

@Serializable
open class ExceptionResponse(
    var problemDetail: ProblemDetail = ProblemDetail("", "", 0, "", ""), //
)
