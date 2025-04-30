package br.unifor.utils

import br.unifor.dto.DTO
import br.unifor.dto.PagedDTO
import br.unifor.exception.APIException
import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.panache.common.Page
import kotlin.reflect.KClass


class PaginacaoUtil<T : Any> {
    fun paginate(query: PanacheQuery<T>, klass: KClass<out DTO>, pageIndex: Int = 1, pageSize: Int = 8): PagedDTO<DTO> {
//        val totalPages = query.pageCount()
//        val pgIndex = maxOf(1, pageIndex.takeIf { it <= totalPages } ?: 1)

        return if (query.count() == 0L) PagedDTO()
        else PagedDTO(
            content = query.page<T>(Page.of(pageIndex - 1, pageSize)).list<T>().map {
            klass.constructors
                .firstOrNull { c -> c.parameters.let { ps -> ps.size == 1 && ps.first().type.classifier == it::class } }
                ?.call(it) ?: throw APIException(
                message = "Nenhum construtor com parâmetro de tipo ${it::class} encontrado.", status = 500
            )
        }, currentPage = query.page().index + 1, totalPages = query.pageCount(), totalElements = query.count())
    }
}
