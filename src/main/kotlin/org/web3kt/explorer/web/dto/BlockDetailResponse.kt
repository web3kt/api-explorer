package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.block.Block
import java.math.BigInteger

/**
 * DTO for {@link io.directional.explorer.domain.block.Block}
 */
data class BlockDetailResponse(
    val id: BigInteger,
    val hash: String,
    val transactionCount: Int,
    val miner: String,
    val totalDifficulty: BigInteger,
    val baseFeePerGas: BigInteger?,
    val size: BigInteger,
    val gasLimit: BigInteger,
    val gasUsed: BigInteger,
    val timestamp: Long,
    val parentHash: String,
    val stateRoot: String,
    val extraData: String,
) {
    companion object {
        fun Block.toDetailResponse(): BlockDetailResponse =
            BlockDetailResponse(
                id = id,
                hash = hash,
                transactionCount = transactionCount,
                miner = miner,
                totalDifficulty = totalDifficulty,
                baseFeePerGas = baseFeePerGas,
                size = size,
                gasLimit = gasLimit,
                gasUsed = gasUsed,
                timestamp = timestamp,
                parentHash = parentHash,
                stateRoot = stateRoot,
                extraData = extraData,
            )
    }
}
