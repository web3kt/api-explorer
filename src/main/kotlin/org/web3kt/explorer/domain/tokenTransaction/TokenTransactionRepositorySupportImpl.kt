package org.web3kt.explorer.domain.tokenTransaction

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.web3kt.explorer.internal.fetchPage

class TokenTransactionRepositorySupportImpl(
    private val query: JPAQueryFactory,
) : TokenTransactionRepositorySupport {
    override fun findByAddress(
        address: String,
        pageable: Pageable,
    ): Page<TokenTransaction> {
        val predicate: Predicate =
            BooleanBuilder()
                .or(QTokenTransaction.tokenTransaction.from.eq(address))
                .or(QTokenTransaction.tokenTransaction.to.eq(address))

        val total =
            query
                .select(QTokenTransaction.tokenTransaction.count())
                .from(QTokenTransaction.tokenTransaction)
                .where(predicate)
                .fetchOne() ?: 0L

        return query
            .selectFrom(QTokenTransaction.tokenTransaction)
            .where(predicate)
            .orderBy(QTokenTransaction.tokenTransaction.id.desc())
            .fetchPage(pageable, total)
    }
}
