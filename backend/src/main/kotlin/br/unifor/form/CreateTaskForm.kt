package br.unifor.form

import br.unifor.serialization.LocalDateTimeJson
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

@Serializable
data class CreateTaskForm(
    @field:NotBlank(message = "Informe o nome da atividade.") //
    @field:Length(max = 25, message = "O nome da atividade deve ter até 25 caracteres.") val nmTask: String, //
    @field:NotBlank(message = "Informe a descrição da atividade.") //
    @field:Length(max = 100, message = "A descrição da atividade deve ter até 100 caracteres.") val dsTask: String, //
    val isPrivateTask: Boolean = true, //
    @field:FutureOrPresent(message = "Informe uma data presente ou futura.") //
    val dtDeadline: LocalDateTimeJson = LocalDate.now().atTime(23, 59, 59), //
)
