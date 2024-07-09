package org.web3kt.explorer.domain.transaction

import org.web3kt.explorer.domain.QJpaRepository
import org.web3kt.explorer.domain.block.Block

interface TransactionRepository :
    QJpaRepository<Transaction, String>,
    TransactionRepositorySupport {
    fun countByBlock(block: Block): Long
}
