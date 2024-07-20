package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.tokenTransaction.TokenTransaction
import java.math.BigInteger

data class TokenTransactionResponse(
    val logId: Long?,
    val timestamp: Long,
    val from: String,
    val to: String,
    val value: BigInteger?,
) {
    companion object {
        fun TokenTransaction.toResponse(): TokenTransactionResponse =
            TokenTransactionResponse(
                logId = log.id,
                timestamp = timestamp,
                from = from,
                to = to,
                value = value,
            )
    }
}
