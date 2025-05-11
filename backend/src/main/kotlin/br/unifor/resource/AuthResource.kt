package br.unifor.resource

import br.unifor.dto.UserTokenDTO
import br.unifor.form.LoginUserForm
import br.unifor.form.RegisterUserForm
import br.unifor.service.AuthService
import br.unifor.utils.getLoggedUser
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.jwt.JsonWebToken

@Path("/auth")
class AuthResource(
    @Inject var jwt: JsonWebToken, //
    @Inject var authService: AuthService, //
) {
    @POST
    @Transactional
    @Path("/register")
    fun registerUser(@Valid form: RegisterUserForm): UserTokenDTO = authService.registerUser(form = form)

    @POST
    @Path("/login")
    fun login(@Valid form: LoginUserForm) = authService.login(form = form)

    @DELETE
    @Transactional
    fun disableUser(@QueryParam("username") username: String) = authService.disableUser(
        loggedUser = jwt.getLoggedUser(), //
        username = username, //
    )
}

