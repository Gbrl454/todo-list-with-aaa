package br.unifor.dto

import br.unifor.entities.todo.Task
import br.unifor.extensions.isComplete
import br.unifor.serialization.LocalDateTimeJson
import kotlinx.serialization.Serializable

@Serializable
data class TaskDetailDTO(
    val hashTask: String, //
    val nmTask: String, //
    val dsTask: String, //
    val isPrivateTask: Boolean, //
    val dtDeadline: LocalDateTimeJson, //
    val dtDo: LocalDateTimeJson?, //
    val wasDone: Boolean, //
    val dtInclusion: LocalDateTimeJson, //
    val isActive: Boolean, //
) {
    constructor(task: Task) : this(
        hashTask = task.hashTask, //
        nmTask = task.nmTask, //
        dsTask = task.dsTask, //
        isPrivateTask = task.isPrivateTask, //
        dtDeadline = task.dtDeadline, //
        dtDo = task.isComplete().first, //
        wasDone = task.isComplete().second, //
        dtInclusion = task.dtInclusion, //
        isActive = task.isActive, //
    )
}
