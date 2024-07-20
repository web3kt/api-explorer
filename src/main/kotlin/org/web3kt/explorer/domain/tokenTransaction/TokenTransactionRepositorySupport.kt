package org.web3kt.explorer.domain.tokenTransaction

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TokenTransactionRepositorySupport {
    fun findByAddress(
        address: String,
        pageable: Pageable,
    ): Page<TokenTransaction>
}
