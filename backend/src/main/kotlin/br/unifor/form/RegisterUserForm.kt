package br.unifor.form

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserForm(
    @field:NotBlank(message = "Informe o usuário.") val username: String = "", //
    @field:NotBlank(message = "Informe o nome completo.") val fullName: String = "", //
    @field:NotBlank(message = "Informe o email.") @field:Email(message = "Email inválido.") val email: String = "", //
    @field:NotBlank(message = "Informe a senha.") val password: String = "", //
    @field:NotBlank(message = "Informe a confirmação da senha.") val passwordConfirmation: String = "", //
)