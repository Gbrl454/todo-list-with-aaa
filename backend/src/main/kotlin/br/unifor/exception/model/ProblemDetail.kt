package br.unifor.exception.model

import kotlinx.serialization.Serializable

@Serializable
data class ProblemDetail(
    val type: String, //
    val title: String?, //
    val status: Int, //
    val detail: String, //
    val instance: String, //
)
