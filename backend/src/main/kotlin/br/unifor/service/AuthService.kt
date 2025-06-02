package br.unifor.service

import br.unifor.dto.UserTokenDTO
import br.unifor.entities.todo.User
import br.unifor.entities.todo.UserEvent
import br.unifor.exception.*
import br.unifor.exception.model.FieldError
import br.unifor.extensions.*
import br.unifor.form.LoginUserForm
import br.unifor.form.RegisterUserForm
import br.unifor.rest.client.KeycloakRestClient
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation

@ApplicationScoped
class AuthService {
    @ConfigProperty(name = "kc-url-base")
    private lateinit var kcUrl: String

    @ConfigProperty(name = "kc-base-realm")
    private lateinit var kcRealm: String

    @ConfigProperty(name = "kc-admin-client")
    private lateinit var kcClientId: String

    @ConfigProperty(name = "kc-admin-client-secret")
    private lateinit var kcClientSecret: String

    @ConfigProperty(name = "kc-public-client")
    private lateinit var kcPublicClient: String

    @Inject
    @field:RestClient
    private lateinit var apiKC: KeycloakRestClient

    private fun buildKc(): Keycloak =  //
        KeycloakBuilder.builder() //
            .serverUrl(kcUrl) //
            .realm(kcRealm) //
            .grantType("client_credentials") //
            .clientId(kcClientId) //
            .clientSecret(kcClientSecret) //
            .build()

    private fun Keycloak.searchUserInkcByUsername(username: String): UserRepresentation = //
        this.realm(kcRealm).users() //
            .search(username, true) //
            .takeIf { it.isNotEmpty() } //
            ?.firstOrNull() //
            ?: throw UserNotFoundException(username = username)

    @Transactional
    fun registerUser(@Valid form: RegisterUserForm): UserTokenDTO {
        val validsFields: MutableList<FieldError?> = mutableListOf(
            validPassword(
                password = form.password, //
                passwordConfirmation = form.passwordConfirmation, //
            ), //
            validUsername(username = form.username), //
        )

        val (firstName: String, middleName: String?, lastName: String) = form.fullName.validAndGetFullName(validsFields = validsFields)

        validsFields.filterNotNull() //
            .takeIf { it.isNotEmpty() } //
            ?.let { throw FieldException(fieldErrors = it) } //

        val user: User = User().apply {
            this.username = form.username
            this.frName = firstName
            this.mdName = middleName
            this.ltName = lastName
            this.email = form.email
            this.persist()
        }

        UserEvent.create(
            user = user, //
            userOperator = user, //
        ).apply { this.persist() }

        registerUserInKc(
            form = form, //
            firstName = firstName, //
            lastName = lastName, //
        )

        return login(
            form = LoginUserForm(
                username = form.username, //
                password = form.password, //
            )
        )
    }

    fun login(form: LoginUserForm): UserTokenDTO = User.list("UPPER(username) = '${form.username.uppercase()}'") //
        .takeIf { it.isNotEmpty() } //
        ?.let { users: List<User> ->
            users.findLast { it.isActive } //
                ?.let {
                    runCatching {
                        apiKC.loginKc(
                            realm = kcRealm, //
                            grantType = "password", //
                            clientId = kcPublicClient, //
                            username = form.username, //
                            password = form.password, //
                        )
                    }.getOrElse { throw TryingLoginException() } //
                        .let { UserTokenDTO(kcUserToken = it) }
                } ?: throw UserNotActiveException(username = form.username)
        } ?: throw UserNotFoundException(username = form.username)

    @Transactional
    fun refreshToken(
        refreshToken: String, //
        loggedUser: User, //
    ): UserTokenDTO = runCatching {
        apiKC.refreshToken(
            realm = kcRealm, //
            client = kcPublicClient, //
            refreshToken = refreshToken, //
            grant = "refresh_token", //
        )
    }.getOrElse { throw TryingLoginException() } //
        .let { UserTokenDTO(kcUserToken = it) }

    fun logout(loggedUser: User, count: Int = 1) {
        val kc: Keycloak = buildKc()
        kc.searchUserInkcByUsername(username = loggedUser.username).let { kcUser: UserRepresentation ->
            runCatching {
                kc.realm(kcRealm).users().get(kcUser.id).logout()
            }.onFailure {
                if (count == 3) throw CloseSessionException()
                else logout(loggedUser = loggedUser, count = count + 1)
            }
        }
        kc.close()
    }

    @Transactional
    fun disableUser(
        loggedUser: User, //
        username: String, //
    ) {
        val kc: Keycloak = buildKc()
        kc.searchUserInkcByUsername(username = username).let { kcUser: UserRepresentation ->
                disableUserInDb(
                    loggedUser = loggedUser, //
                    username = username, //
                )

                val userResource = kc.realm(kcRealm).users().get(kcUser.id)
                kcUser.isEnabled = false
                userResource.update(kcUser)
            }
        kc.close()
    }

    private fun registerUserInKc(
        form: RegisterUserForm, //
        firstName: String, //
        lastName: String, //
    ) {
        val kc: Keycloak = buildKc()

        kc.realm(kcRealm).users() //
            .create(
                UserRepresentation().apply {
                    this.username = form.username
                    this.firstName = firstName
                    this.lastName = lastName
                    this.email = form.email
                    this.isEnabled = true
                    this.isEmailVerified = true
                    this.groups = listOf("TodoGroup")
                }) //
            .let { response ->
                if (response.status != 201) throw RegisterKcException()
                val userId = kotlin.runCatching { response.location.path.replace(".*/([^/]+)$".toRegex(), "$1") } //
                    .getOrNull() ?: throw RegisterKcException()

                (kc.realm(kcRealm) //
                    .users() //
                    .get(userId) //
                    ?: throw RegisterKcException()) //
                    .apply {
                        this.resetPassword(CredentialRepresentation().apply {
                            this.isTemporary = false
                            this.type = CredentialRepresentation.PASSWORD
                            this.value = form.password
                        })
                    }
            }

        kc.close()
    }

    private fun validPassword(
        password: String, //
        passwordConfirmation: String? = null, //
    ): FieldError? = setOfNotNull(password, passwordConfirmation) //
        .takeIf { it.size != 1 } //
        ?.let { FieldError(field = "passwordConfirmation", message = "As senhas informadas são diferentes.") } //
        ?: Any().takeIf {
            !(password.hasUppercaseLetters //
                    && password.hasLowercaseLetters //
                    && password.hasNumbers //
                    && password.hasSpecialCharacters)
        }?.let {
            FieldError(
                field = "password", //
                message = "A senha deve ser forte. Certifique-se de que ela contenha pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial (como !, @, #, \$, etc.).", //
            )
        }

    private fun validUsername(username: String): FieldError? = (username.takeIf { it.hasSpecialCharacters } //
        ?.let { "Um usuário não pode conter caracteres especiais." } //
        ?: User.list("UPPER(username) = '${username.uppercase()}' AND isActive = true") //
            .takeIf { it.isNotEmpty() } //
            ?.let { "Já existe uma pessoa cadastrada com esse usuário." } //
            )?.let { FieldError(field = "username", message = it) }

    private fun String.validAndGetFullName(validsFields: MutableList<FieldError?>): Triple<String, String?, String> =
        try {
            this.separateFullName()
        } catch (e: Exception) {
            validsFields.add(
                FieldError(
                    field = "fullName", //
                    message = e.message ?: "Erro ao processar campo fullName.", //
                )
            )
            Triple("", null, "")
        }

    private fun disableUserInDb(
        loggedUser: User, //
        username: String, //
    ): User = User.findByUsername(username = username).apply {
        this.isActive = false
        this.persist()

        UserEvent.disable(
            user = this, //
            userOperator = loggedUser, //
        ).persist()
    }
}
