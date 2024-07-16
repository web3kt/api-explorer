package org.web3kt.explorer.domain.log

import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import org.web3kt.core.protocol.serializer.Integer
import org.web3kt.explorer.domain.LongEntity
import org.web3kt.explorer.domain.topic.Topic
import org.web3kt.explorer.domain.transaction.Transaction

@Entity
class Log(
    val logIndex: Integer,
    val removed: Boolean,
    @ManyToOne val transaction: Transaction,
    val address: String,
    val data: String,
    @ManyToMany val topics: List<Topic>,
) : LongEntity()
