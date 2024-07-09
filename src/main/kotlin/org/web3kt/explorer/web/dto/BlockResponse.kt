package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.block.Block
import java.math.BigInteger

/**
 * DTO for {@link io.directional.explorer.domain.block.Block}
 */
data class BlockResponse(
    val id: BigInteger,
    val transactionCount: Int,
    val miner: String,
    val gasLimit: BigInteger,
    val gasUsed: BigInteger,
    val timestamp: Long,
) {
    companion object {
        fun Block.toResponse(): BlockResponse =
            BlockResponse(
                id = id,
                transactionCount = transactionCount,
                miner = miner,
                gasLimit = gasLimit,
                gasUsed = gasUsed,
                timestamp = timestamp,
            )
    }
}
