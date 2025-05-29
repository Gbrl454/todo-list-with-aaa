package br.unifor.dto

import br.unifor.entities.todo.Task
import br.unifor.serialization.LocalDateTimeJson
import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO(
    val hashTask: String, //
    val nmTask: String, //
    val dtDeadline: LocalDateTimeJson, //
    val dtDo: LocalDateTimeJson?, //
    val wasDone: Boolean, //
) {
    constructor(task: Task) : this(
        hashTask = task.hashTask, //
        nmTask = task.nmTask, //
        dtDeadline = task.dtDeadline, //
        dtDo = task.dtDo, //
        wasDone = task.dtDo != null, //
    )
}