package br.unifor.resource

import jakarta.inject.Inject
import jakarta.ws.rs.Path
import org.eclipse.microprofile.jwt.JsonWebToken

@Path("/works")
class WorksResource(
    @Inject var jwt: JsonWebToken, //
) {}

