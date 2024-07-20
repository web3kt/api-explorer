package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.transaction.Transaction
import java.math.BigInteger

/**
 * DTO for {@link io.directional.explorer.domain.transaction.Transaction}
 */
data class TransactionResponse(
    val id: String,
    val blockId: BigInteger,
    val timestamp: Long,
    val from: String,
    val to: String?,
    val status: String?,
    val value: BigInteger,
    val gasUsed: BigInteger,
    val gasLimit: BigInteger,
    val gasPrice: BigInteger,
) {
    companion object {
        fun Transaction.toResponse() =
            TransactionResponse(
                id = id,
                blockId = block.id,
                timestamp = timestamp,
                from = from,
                to = to,
                status = status,
                value = value,
                gasUsed = gasUsed,
                gasLimit = gas,
                gasPrice = gasPrice,
            )
    }
}
