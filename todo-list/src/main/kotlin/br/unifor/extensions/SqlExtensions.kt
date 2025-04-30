package br.unifor.extensions

import java.time.temporal.Temporal

fun List<Any>.toSql(separator: String, marks: Boolean = true): String =
    this.joinToString(prefix = "(", separator = separator, postfix = ")") { value ->
        when (value) {
            is String -> marks.takeIf { it }?.let { "'$value'" } ?: "$value"
            is Number -> "$value"
            is Enum<*> -> "$value"
            else -> marks.takeIf { it }?.let { "'$value'" } ?: "$value"

        }
    }

val List<Any>.sql: String get() = this.toSql(separator = ", ")

fun List<Any>.toSqlAnd(marks: Boolean = true): String = this.toSql(separator = " AND ", marks = marks)

fun List<Any>.toSqlOr(marks: Boolean = true): String = this.toSql(separator = " OR ", marks = marks)

fun Temporal.toSqlDate(pattern: String): String = "TO_DATE('$this', '$pattern')"

val Temporal.toSqlDateFull: String get() = this.toSqlDate(pattern = "yyyy-mm-dd")