package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.internalTransaction.InternalTransaction
import org.web3kt.explorer.web.dto.TransactionResponse.Companion.toResponse
import java.math.BigInteger

data class InternalTransactionResponse(
    val transaction: TransactionResponse,
    val timestamp: Long,
    val from: String,
    val to: String,
    val value: BigInteger?,
) {
    companion object {
        fun InternalTransaction.toResponse(): InternalTransactionResponse =
            InternalTransactionResponse(
                transaction = transaction.toResponse(),
                timestamp = timestamp,
                from = from,
                to = to,
                value = value,
            )
    }
}
