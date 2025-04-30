package br.unifor.utils

import br.unifor.core.entities.ca.FebrabanFeriados
import br.unifor.exception.APIException
import br.unifor.extensions.joinToStringList
import java.time.DayOfWeek
import java.time.LocalDate

val holidays: List<FebrabanFeriados> by lazy { FebrabanFeriados.listAll() }

val yearsHolidaysLoading: List<Int> by lazy { holidays.map { it.dtFeriado.year }.distinct() }

fun isWeekend(date: LocalDate): Boolean = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(date.dayOfWeek)

fun isHoliday(date: LocalDate): Boolean = getHolidayFromDate(date) != null

fun getHolidayFromDate(dtFeriado: LocalDate): FebrabanFeriados? = holidays.firstOrNull { it.dtFeriado == dtFeriado }

fun manyBusinessDaysBetween(dtInitial: LocalDate, dtFinal: LocalDate): Int =
    generateSequence(dtInitial) { it.plusDays(1) }.filter { isBusinessDay(it) }.takeWhile { !it.isAfter(dtFinal) }
        .toList().size

fun isBusinessDay(date: LocalDate): Boolean = !isWeekend(date) && !isHoliday(date)

fun nextBusinessDay(startDate: LocalDate = LocalDate.now(), businessDaysAhead: Int = 1): LocalDate {
    if (!yearsHolidaysLoading.contains(startDate.year)) throw APIException(
        message = "A busca por dias úteis só funciona para os anos de ${yearsHolidaysLoading.joinToStringList()}.",
        status = 400
    )

    var date = startDate
    repeat(businessDaysAhead) {
        do {
            date = date.plusDays(1)
        } while (!isBusinessDay(date))
    }
    return if (!yearsHolidaysLoading.contains(date.year)) throw APIException(
        message = "A busca por dias úteis só tem garantia para os anos de ${yearsHolidaysLoading.joinToStringList()}.",
        status = 400
    ) else date
}