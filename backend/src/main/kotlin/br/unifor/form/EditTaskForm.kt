package br.unifor.form

import br.unifor.serialization.LocalDateTimeJson
import kotlinx.serialization.Serializable

@Serializable
data class EditTaskForm(
    val nmTask: String? = null, //
    val dsTask: String? = null, //
    val isPrivateTask: Boolean? = null, //
    val dtDeadline: LocalDateTimeJson? = null, //
)
