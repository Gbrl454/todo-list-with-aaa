package br.unifor.exception

import kotlinx.serialization.Serializable

@Serializable
open class APIException(
    val title: String? = null, //
    override val message: String, //
    val status: Int, //
) : RuntimeException(message)
