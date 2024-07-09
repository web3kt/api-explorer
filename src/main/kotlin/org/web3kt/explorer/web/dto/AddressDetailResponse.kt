package org.web3kt.explorer.web.dto

import java.math.BigInteger

data class AddressDetailResponse(
    val id: String,
    val balance: BigInteger,
    val isContract: Boolean,
)
