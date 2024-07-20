package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.tokenTransaction.TokenTransaction
import org.web3kt.explorer.web.dto.TokenResponse.Companion.toResponse
import org.web3kt.explorer.web.dto.TransactionResponse.Companion.toResponse
import java.math.BigInteger

data class TokenTransactionResponse(
    val transaction: TransactionResponse,
    val timestamp: Long,
    val token: TokenResponse,
    val from: String,
    val to: String,
    val value: BigInteger?,
) {
    companion object {
        fun TokenTransaction.toResponse(): TokenTransactionResponse =
            TokenTransactionResponse(
                transaction = log.transaction.toResponse(),
                timestamp = timestamp,
                token = token.toResponse(),
                from = from,
                to = to,
                value = value,
            )
    }
}
