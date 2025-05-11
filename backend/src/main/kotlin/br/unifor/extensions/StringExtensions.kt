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

val String.hasUppercaseLetters: Boolean get() = this.contains(Regex("[A-Z]"))

val String.hasLowercaseLetters: Boolean get() = this.contains(Regex("[a-z]"))

val String.hasNumbers: Boolean get() = this.contains(Regex("[0-9]"))

val String.hasSpecialCharacters: Boolean get() = this.contains(Regex("[^a-zA-Z0-9]"))

fun String.separateFullName(): Triple<String, String?, String> =
    this.trim().split(Regex("\\s+")).let { nameParts: List<String> ->
        when (nameParts.size) {
            1 -> throw RuntimeException("Informe ao menos nome e sobrenome.")
            2 -> null
            else -> {
                nameParts.subList(1, nameParts.size - 1).joinToString(" ")
            }
        }.let { Triple(nameParts.first(), it, nameParts.last()) }
    }