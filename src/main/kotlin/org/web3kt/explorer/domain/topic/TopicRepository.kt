package org.web3kt.explorer.domain.topic

import org.springframework.data.jpa.repository.JpaRepository

interface TopicRepository : JpaRepository<Topic, Long> {
    fun findByValue(value: String): Topic?
}
