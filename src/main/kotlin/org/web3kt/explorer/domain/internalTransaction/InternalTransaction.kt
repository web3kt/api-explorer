package org.web3kt.explorer.domain.internalTransaction

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.web3kt.explorer.domain.LongEntity
import org.web3kt.explorer.domain.transaction.Transaction
import java.math.BigInteger

@Entity
class InternalTransaction(
    @ManyToOne val transaction: Transaction,
    val callType: String?,
    val from: String,
    val gas: BigInteger?,
    @Column(columnDefinition = "text") val input: String?,
    val to: String,
    val value: BigInteger?,
    val author: String?,
    val rewardType: String?,
    val gasUsed: BigInteger?,
    val output: String?,
    val type: String?,
    val subTraces: Int?,
    // val traceAddress: List<Int>,
) : LongEntity()
