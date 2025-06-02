package br.unifor.rest.client

import br.unifor.rest.model.KcToken
import br.unifor.rest.model.KcUserToken
import jakarta.ws.rs.*
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("/")
@RegisterRestClient(configKey = "kc-api")
interface KeycloakRestClient {
    @POST
    @Consumes(value = ["application/x-www-form-urlencoded"])
    @Path("/realms/{realm}/protocol/openid-connect/token")
    fun getServiceToken(
        @PathParam("realm") realm: String, //
        @FormParam("grant_type") grantType: String, //
        @FormParam("client_id") clientId: String, //
        @FormParam("client_secret") clientSecret: String, //
    ): KcToken

    @POST
    @Path("/realms/{realm}/protocol/openid-connect/token")
    @Consumes(value = ["application/x-www-form-urlencoded"])
    fun loginKc(
        @PathParam("realm") realm: String, //
        @FormParam("grant_type") grantType: String, //
        @FormParam("client_id") clientId: String, // //
        @FormParam("username") username: String, //
        @FormParam("password") password: String, //
    ): KcUserToken


    @POST
    @Consumes(value = ["application/x-www-form-urlencoded"])
    @Path("auth/realms/{realm}/protocol/openid-connect/token")
    fun refreshToken(
        @PathParam("realm") realm: String, //
        @FormParam("client_id") client: String, //
        @FormParam("refresh_token") refreshToken: String, //
        @FormParam("grant_type") grant: String, //
    ): KcUserToken
}