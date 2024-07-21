package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.token.Token

data class TokenResponse(
    val id: String,
    val name: String?,
    val symbol: String?,
    val decimals: Int?,
) {
    companion object {
        fun Token.toResponse(): TokenResponse = TokenResponse(id, name, symbol, decimals)
    }
}
