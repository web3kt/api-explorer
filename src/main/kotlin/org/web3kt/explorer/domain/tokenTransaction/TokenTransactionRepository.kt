package org.web3kt.explorer.domain.tokenTransaction

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TokenTransactionRepository :
    JpaRepository<TokenTransaction, Long>,
    TokenTransactionRepositorySupport {
    fun findByTokenId(
        token: String,
        pageable: Pageable,
    ): Page<TokenTransaction>
}
