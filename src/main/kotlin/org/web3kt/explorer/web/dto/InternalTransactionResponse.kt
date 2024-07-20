package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.internalTransaction.InternalTransaction
import java.math.BigInteger

data class InternalTransactionResponse(
    val transactionId: String,
    val timestamp: Long,
    val from: String,
    val to: String,
    val value: BigInteger?,
) {
    companion object {
        fun InternalTransaction.toResponse(): InternalTransactionResponse =
            InternalTransactionResponse(
                transactionId = transaction.id,
                timestamp = timestamp,
                from = from,
                to = to,
                value = value,
            )
    }
}
