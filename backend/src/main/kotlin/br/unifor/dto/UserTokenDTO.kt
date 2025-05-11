package br.unifor.dto

import br.unifor.rest.model.KcUserToken
import kotlinx.serialization.Serializable

@Serializable
data class UserTokenDTO(
    val accessToken: String, //
    val expiresIn: Long, //
    val refreshExpiresIn: Long, //
    val refreshToken: String, //
) {
    constructor(kcUserToken: KcUserToken) : this(
        accessToken = kcUserToken.access_token, //
        expiresIn = kcUserToken.expires_in, //
        refreshExpiresIn = kcUserToken.refresh_expires_in, //
        refreshToken = kcUserToken.refresh_token, //
    )
}
