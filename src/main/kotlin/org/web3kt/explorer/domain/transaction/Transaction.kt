package org.web3kt.explorer.domain.transaction

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.web3kt.explorer.domain.block.Block
import org.web3kt.explorer.domain.internalTransaction.InternalTransaction
import org.web3kt.explorer.domain.log.Log
import java.math.BigInteger

@Entity
@Table(
    indexes = [
        Index(name = "idx_transaction_from", columnList = "from"),
        Index(name = "idx_transaction_to", columnList = "to"),
    ],
)
class Transaction(
    @Id val id: String,
    @ManyToOne(fetch = FetchType.LAZY) val block: Block,
    val timestamp: Long,
    val contractAddress: String?,
    val cumulativeGasUsed: BigInteger,
    val from: String,
    val gasUsed: BigInteger,
    val effectiveGasPrice: BigInteger,
    @Column(columnDefinition = "text") val logsBloom: String,
    val status: String?,
    val revertReason: String?,
    val to: String,
    val transactionIndex: BigInteger,
    val type: String,
    val chainId: BigInteger? = null,
    val gas: BigInteger,
    val gasPrice: BigInteger,
    @Column(columnDefinition = "longtext") val input: String,
    val maxFeePerGas: BigInteger? = null,
    val maxPriorityFeePerGas: BigInteger? = null,
    val nonce: BigInteger,
    val r: String,
    val s: String,
    val v: String,
    val value: BigInteger,
    val yParity: String? = null,
) {
    @OneToMany(mappedBy = "transaction")
    val logs: List<Log> = emptyList()

    @OneToMany(mappedBy = "transaction")
    val internalTransactions: List<InternalTransaction> = emptyList()
}
