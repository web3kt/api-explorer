package org.web3kt.explorer.domain.topic

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.web3kt.explorer.domain.LongEntity

@Entity
class Topic(
    @Column(unique = true) val value: String,
    val name: String?,
) : LongEntity()
