package org.web3kt.explorer.internal

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.web3kt.core.Web3
import org.web3kt.core.protocol.dto.Tag
import org.web3kt.explorer.domain.QJpaRepository

fun <T> Page<T>.toPagedModel(): PagedModel<T> = PagedModel(this)

fun <T, ID> QJpaRepository<T, ID>.findAll(
    pageable: Pageable,
    block: BooleanBuilder.() -> Predicate,
): Page<T> = findAll(block(BooleanBuilder()), pageable)

fun <T> BooleanBuilder.filter(
    it: T?,
    action: (it: T) -> Predicate,
): BooleanBuilder {
    if (it == null) return this
    return and(action(it))
}

fun <T> JPAQuery<T>.fetchPage(
    pageable: Pageable,
    total: Long,
): Page<T> =
    if (pageable.isPaged) {
        val content =
            this
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        PageImpl(content, pageable, total)
    } else {
        PageImpl(this.fetch())
    }

suspend fun Web3.isContract(address: String): Boolean =
    try {
        eth.getCode(address, Tag.LATEST) != "0x"
    } catch (exception: NullPointerException) {
        true
    }
