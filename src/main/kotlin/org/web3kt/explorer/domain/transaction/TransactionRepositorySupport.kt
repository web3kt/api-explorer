package org.web3kt.explorer.domain.transaction

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TransactionRepositorySupport {
    fun findByAddress(
        address: String,
        pageable: Pageable,
    ): Page<Transaction>
}
