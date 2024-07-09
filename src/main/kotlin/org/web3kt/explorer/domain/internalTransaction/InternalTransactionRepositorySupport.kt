package org.web3kt.explorer.domain.internalTransaction

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface InternalTransactionRepositorySupport {
    fun findByAddress(
        address: String,
        pageable: Pageable,
    ): Page<InternalTransaction>
}
