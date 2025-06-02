package br.unifor.extensions

import br.unifor.entities.todo.Task
import br.unifor.exception.DeadlineDateLessCurrentDateException
import java.time.LocalDateTime

fun Task.isComplete(): Pair<LocalDateTime?, Boolean> = this.dtDo.let { it to (it != null) }

fun LocalDateTime.testDeadlineDateLessCurrentDate(date: LocalDateTime) =
    takeIf { isAfter(date) }?.let { throw DeadlineDateLessCurrentDateException() }

inline fun <V> updateIfChanged(
    current: () -> V, //
    update: (V) -> Unit, //
    newValue: V?, //
    onChange: () -> Unit = {}, //
): Boolean = newValue?.takeIf { it != current() } //
    ?.let {
        update(it)
        onChange()
        true
    } ?: false

