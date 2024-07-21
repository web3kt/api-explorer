package org.web3kt.explorer.domain.token

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Token(
    @Id val id: String,
    val name: String?,
    val symbol: String?,
    val decimals: Int?,
)
