package br.unifor.form

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class LoginUserForm(
    @field:NotBlank(message = "Informe o usuário.") val username: String = "", //
    @field:NotBlank(message = "Informe a senha.") val password: String = "", //
)
