package org.web3kt.explorer.web.dto

import java.math.BigInteger

data class TokenDetailResponse(
    val id: String,
    val name: String,
    val symbol: String,
    val decimals: Int,
    val totalSupply: BigInteger,
)
