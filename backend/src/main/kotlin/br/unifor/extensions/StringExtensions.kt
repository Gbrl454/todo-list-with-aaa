package br.unifor.extensions

fun String.capitalizeFirst(): String = this.lowercase().replaceFirstChar { it.uppercase() }

fun String.camelCase(): String = this.lowercase().split(" ").joinToString(" ") { it.capitalizeFirst() }

fun <T> List<T>.joinToStringList(): String = when (this.size) {
    0 -> ""
    1 -> this.first().toString()
    else -> this.joinToString("") { value ->
        if (value == this.last()) " e $value" else if (value == this.first()) value.toString() else ", ${value.toString()}"
    }
}