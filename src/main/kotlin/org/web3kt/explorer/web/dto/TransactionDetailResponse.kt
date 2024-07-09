package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.transaction.Transaction
import org.web3kt.explorer.web.dto.BlockResponse.Companion.toResponse
import org.web3kt.explorer.web.dto.InternalTransactionResponse.Companion.toResponse
import org.web3kt.explorer.web.dto.LogResponse.Companion.toResponse
import java.math.BigInteger

/**
 * DTO for {@link io.directional.explorer.domain.transaction.Transaction}
 */
data class TransactionDetailResponse(
    val id: String,
    val block: BlockResponse,
    val contractAddress: String?,
    val from: String,
    val to: String?,
    val status: String?,
    val revertReason: String?,
    val value: BigInteger,
    val gasUsed: BigInteger,
    val gasLimit: BigInteger,
    val gasPrice: BigInteger,
    val maxPriorityFeePerGas: BigInteger?,
    val maxFeePerGas: BigInteger?,
    val input: String,
    val logs: List<LogResponse>,
    val internalTransactions: List<InternalTransactionResponse>,
) {
    companion object {
        fun Transaction.toDetailResponse() =
            TransactionDetailResponse(
                id = id,
                block = block.toResponse(),
                contractAddress = contractAddress,
                from = from,
                to = to,
                status = status,
                revertReason = revertReason,
                value = value,
                gasLimit = gas,
                gasUsed = gasUsed,
                gasPrice = gasPrice,
                maxPriorityFeePerGas = maxPriorityFeePerGas,
                maxFeePerGas = maxFeePerGas,
                input = input,
                logs = logs.map { it.toResponse() },
                internalTransactions = internalTransactions.map { it.toResponse() },
            )
    }
}
