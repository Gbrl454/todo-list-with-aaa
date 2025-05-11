package br.unifor.rest.model

import kotlinx.serialization.Serializable

@Serializable
data class KcUserToken(
    val access_token: String, //
    val expires_in: Long, //
    val refresh_expires_in: Long, //
    val refresh_token: String, //
    val token_type: String, //
    val `not-before-policy`: Int, //
    val session_state: String, //
    val scope: String, //
)
