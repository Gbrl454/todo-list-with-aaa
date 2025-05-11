package br.unifor.dto

import kotlinx.serialization.Serializable

@Serializable
sealed interface DTO

@Serializable
data class PagedDTO<T : DTO>(
    val content: List<T>, //
    val currentPage: Int, //
    val totalPages: Int, //
    val size: Int, //
    val totalElements: Long, //
    val isFirst: Boolean, //
    val isLast: Boolean, //
) : DTO {
    constructor(
        content: List<T> = emptyList(), //
        currentPage: Int = 1, //
        totalPages: Int = 0, //
        totalElements: Long = 0L, //
    ) : this(
        content = content, //
        currentPage = currentPage, //
        totalPages = totalPages, //
        size = content.size, //
        totalElements = totalElements,  //
        isFirst = currentPage == 1,  //
        isLast = currentPage >= totalPages, //
    )
}
