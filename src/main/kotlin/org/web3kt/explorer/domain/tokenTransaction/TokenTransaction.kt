package org.web3kt.explorer.domain.tokenTransaction

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.web3kt.explorer.domain.log.Log
import org.web3kt.explorer.domain.token.Token
import java.math.BigInteger

@Entity
@Table(
    indexes = [
        Index(name = "idx_tokenTransaction_from", columnList = "from"),
        Index(name = "idx_tokenTransaction_to", columnList = "to"),
        Index(name = "idx_tokenTransaction_timestamp", columnList = "timestamp"),
    ],
)
class TokenTransaction(
    @Id val id: Long,
    @OneToOne val log: Log,
    val timestamp: Long,
    @ManyToOne val token: Token,
    val from: String,
    val to: String,
    val value: BigInteger,
)
