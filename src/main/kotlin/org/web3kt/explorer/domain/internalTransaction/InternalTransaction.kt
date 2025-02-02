package org.web3kt.explorer.domain.internalTransaction

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.web3kt.explorer.domain.LongEntity
import org.web3kt.explorer.domain.transaction.Transaction
import java.math.BigInteger

@Entity
@Table(
    indexes = [
        Index(name = "idx_internalTransaction_from", columnList = "from"),
        Index(name = "idx_internalTransaction_to", columnList = "to"),
        Index(name = "idx_internalTransaction_timestamp", columnList = "timestamp"),
    ],
)
class InternalTransaction(
    @ManyToOne val transaction: Transaction,
    val timestamp: Long,
    val callType: String?,
    val from: String,
    val gas: BigInteger?,
    @Column(columnDefinition = "longtext") val input: String?,
    val to: String,
    @Column(precision = 65) val value: BigInteger?,
    val author: String?,
    val rewardType: String?,
    val gasUsed: BigInteger?,
    @Column(columnDefinition = "longtext") val output: String?,
    val type: String?,
    val subTraces: Int?,
    // val traceAddress: List<Int>,
) : LongEntity()
