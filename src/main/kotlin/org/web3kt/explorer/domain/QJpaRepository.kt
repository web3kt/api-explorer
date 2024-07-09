package org.web3kt.explorer.domain

import org.springframework.data.querydsl.ListQuerydslPredicateExecutor
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.ListPagingAndSortingRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface QJpaRepository<T, ID> :
    ListCrudRepository<T, ID>,
    ListPagingAndSortingRepository<T, ID>,
    ListQuerydslPredicateExecutor<T> {
    fun existsBy(): Boolean
}
