package org.web3kt.explorer.domain.block

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.math.BigInteger

@Entity
@Table(
    indexes = [
        Index(name = "idx_block_timestamp", columnList = "timestamp"),
    ],
)
class Block(
    @Id val id: BigInteger,
    val baseFeePerGas: BigInteger?,
    val blobGasUsed: BigInteger?,
    val difficulty: BigInteger,
    val excessBlobGas: BigInteger?,
    @Column(columnDefinition = "text") val extraData: String,
    val gasLimit: BigInteger,
    val gasUsed: BigInteger,
    val hash: String,
    @Column(columnDefinition = "text") val logsBloom: String,
    val miner: String,
    val mixHash: String,
    val nonce: BigInteger,
    val parentBeaconBlockRoot: String?,
    val parentHash: String,
    val receiptsRoot: String,
    val sha3Uncles: String,
    val size: BigInteger,
    val stateRoot: String,
    val timestamp: Long,
    val totalDifficulty: BigInteger,
    val transactionCount: Int,
    val transactionsRoot: String,
    // val uncles: List<String>,
    // val withdrawals: List<Withdrawal>?,
    // val withdrawalsRoot: String?,
)
