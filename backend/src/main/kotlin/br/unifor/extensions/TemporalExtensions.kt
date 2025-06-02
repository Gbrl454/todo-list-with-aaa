package br.unifor.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toStringDate(): String = this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

fun LocalDateTime.toStringTime(): String = this.format(DateTimeFormatter.ofPattern("hh:mm:ss"))
