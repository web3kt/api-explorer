package org.web3kt.explorer.domain.log

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.web3kt.core.protocol.serializer.Integer
import org.web3kt.explorer.domain.LongEntity
import org.web3kt.explorer.domain.topic.Topic
import org.web3kt.explorer.domain.transaction.Transaction

@Entity
@Table(
    indexes = [
        Index(name = "idx_log_address", columnList = "address"),
    ],
)
class Log(
    @ManyToOne val transaction: Transaction,
    val timestamp: Long,
    val logIndex: Integer,
    val removed: Boolean,
    val address: String,
    @Column(columnDefinition = "longtext") val data: String,
    @ManyToMany val topics: List<Topic>,
) : LongEntity()
