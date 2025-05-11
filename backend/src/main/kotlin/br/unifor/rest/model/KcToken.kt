package br.unifor.rest.model

import kotlinx.serialization.Serializable

@Serializable
data class KcToken(
    val access_token: String, //
    val expires_in: Long, //
    val refresh_expires_in: Long, //
    val token_type: String, //
    val `not-before-policy`: Int, //
    val scope: String, //
)
