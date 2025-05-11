package br.unifor.extensions

import br.unifor.exception.APIException
import br.unifor.model.ProblemDetail
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo

fun APIException.makeDefaultResponse(uriInfo: UriInfo): Response? = ProblemDetail(
    type = this::class.simpleName.toString(), //
    title = this.title ?: this::class.simpleName, //
    status = this.status, //
    detail = this.message, //
    instance = uriInfo.requestUri.toString(), //
).let { problemDetail ->
    Response.status(problemDetail.status) //
        .entity(problemDetail) //
        .header("X-RFC7807-Message", this.message) //
        .type("application/problem+json") //
        .build() //
}
