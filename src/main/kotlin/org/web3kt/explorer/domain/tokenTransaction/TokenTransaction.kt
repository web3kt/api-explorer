package org.web3kt.explorer.domain.tokenTransaction

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.web3kt.explorer.domain.log.Log
import java.math.BigInteger

@Entity
@Table(
    indexes = [
        Index(name = "idx_tokenTransaction_from", columnList = "from"),
        Index(name = "idx_tokenTransaction_to", columnList = "to"),
        Index(name = "idx_tokenTransaction_token", columnList = "token"),
    ],
)
class TokenTransaction(
    @Id val id: Long,
    @OneToOne val log: Log,
    val timestamp: Long,
    val token: String,
    val from: String,
    val to: String,
    val value: BigInteger,
)
