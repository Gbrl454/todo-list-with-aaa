package br.unifor.utils

import br.unifor.core.entities.ca.fin.PeriodoFinanceiro
import br.unifor.core.enums.PeriodoSituacao
import br.unifor.exception.APIException
import br.unifor.extensions.toSqlDateFull
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal

fun <T> List<T>.validAndGetUnique(): Pair<Boolean, T?> = Pair((this.toSet().size == 1), this.firstOrNull())

fun <T> List<T>.validateUniqueOrThrow(message: String): T? =
    this.validateUniqueOrThrow(exception = APIException(message = message, status = 400))

fun <T> List<T>.validateUniqueOrThrow(exception: APIException): T? =
    (this.validAndGetUnique().takeIf { it.first } ?: throw exception).second

fun <T> validUnicidade(campo: T, valor: T, mensagemErro: String): T = campo?.let {
    if (it != valor) throw APIException(message = mensagemErro, status = 400) else it
} ?: valor

@Suppress("UNCHECKED_CAST")
fun <T> medianaData(datas: List<T>): T where T : Temporal, T : Comparable<T> = datas.sorted().let {
    if (it.size % 2 != 0) it[it.size / 2]
    else (it.size / 2).let { mid ->
        it[mid - 1].let { dt1 ->
            (dt1.plus(
                ChronoUnit.DAYS.between(dt1, it[mid]) / 2, ChronoUnit.DAYS
            ) as T)
        }
    }
}

fun <T> allItemsEqual(list: List<T>): Boolean = list.all { it == list.firstOrNull() }

fun <T> allItemsEqualFirstOrNull(list: List<T>): Pair<T?, Boolean> = list.firstOrNull() to allItemsEqual(list = list)

fun <T> maiorFrequencia(dados: List<T>): T? = dados.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key

fun verify(condition: Boolean, exception: Exception) {
    if (!condition) throw exception
}

fun <T> isPeriodoFinanceiroAberto(data: T): Boolean where T : Temporal, T : Comparable<T> =
    PeriodoFinanceiro.count("dtInicio <= ${data.toSqlDateFull} AND dtTermino >= ${data.toSqlDateFull} AND situacao != ${PeriodoSituacao.ABERTO}") == 0L
