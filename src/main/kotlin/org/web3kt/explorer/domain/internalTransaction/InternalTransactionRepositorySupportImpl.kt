package org.web3kt.explorer.domain.internalTransaction

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.web3kt.explorer.internal.fetchPage

class InternalTransactionRepositorySupportImpl(
    private val query: JPAQueryFactory,
) : InternalTransactionRepositorySupport {
    override fun findByAddress(
        address: String,
        pageable: Pageable,
    ): Page<InternalTransaction> {
        val predicate: Predicate =
            BooleanBuilder()
                .or(QInternalTransaction.internalTransaction.from.eq(address))
                .or(QInternalTransaction.internalTransaction.to.eq(address))

        val total =
            query
                .select(QInternalTransaction.internalTransaction.count())
                .from(QInternalTransaction.internalTransaction)
                .where(predicate)
                .fetchOne() ?: 0L

        return query
            .selectFrom(QInternalTransaction.internalTransaction)
            .where(predicate)
            .orderBy(
                QInternalTransaction.internalTransaction.transaction.block.id
                    .desc(),
                QInternalTransaction.internalTransaction.transaction.transactionIndex
                    .desc(),
            ).fetchPage(pageable, total)
    }
}
