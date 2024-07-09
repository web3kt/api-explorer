package org.web3kt.explorer.domain.transaction

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.web3kt.explorer.internal.fetchPage

class TransactionRepositorySupportImpl(
    private val query: JPAQueryFactory,
) : TransactionRepositorySupport {
    override fun findByAddress(
        address: String,
        pageable: Pageable,
    ): Page<Transaction> {
        val predicate: Predicate =
            BooleanBuilder()
                .or(QTransaction.transaction.from.eq(address))
                .or(QTransaction.transaction.to.eq(address))

        val total =
            query
                .select(QTransaction.transaction.count())
                .from(QTransaction.transaction)
                .where(predicate)
                .fetchOne() ?: 0L

        return query
            .selectFrom(QTransaction.transaction)
            .where(predicate)
            .orderBy(
                QTransaction.transaction.block.id
                    .desc(),
                QTransaction.transaction.transactionIndex.desc(),
            ).fetchPage(pageable, total)
    }
}
