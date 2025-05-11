package br.unifor.service

import br.unifor.entities.todo.User
import br.unifor.entities.todo.UserEvent
import br.unifor.exception.FieldException
import br.unifor.extensions.*
import br.unifor.form.RegisterUserForm
import br.unifor.exception.model.FieldError
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.validation.Valid

@ApplicationScoped
class AuthService {
    @Transactional
    fun registerUser(@Valid form: RegisterUserForm) {
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
        ).apply {
            this.persist()
        }
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

    private fun validUsername(username: String): FieldError? = (
            // Verifica se o usuário possuiu caracteres especiais
            username.takeIf { it.hasSpecialCharacters } //
                ?.let { "Um usuário não pode conter caracteres especiais." } //
            // Verifica se já existe um usuário igual no banco
                ?: User.list("UPPER(username) = '${username.uppercase()}'") //
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
}
