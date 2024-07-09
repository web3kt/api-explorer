package org.web3kt.explorer.domain.internalTransaction

import org.springframework.data.jpa.repository.JpaRepository

interface InternalTransactionRepository :
    JpaRepository<InternalTransaction, Long>,
    InternalTransactionRepositorySupport
