package org.web3kt.explorer.web.dto

import org.web3kt.core.protocol.serializer.Integer
import org.web3kt.explorer.domain.log.Log
import org.web3kt.explorer.web.dto.TopicResponse.Companion.toResponse
import org.web3kt.explorer.web.dto.TransactionResponse.Companion.toResponse

data class LogResponse(
    val transaction: TransactionResponse,
    val timestamp: Long,
    val logIndex: Integer,
    val removed: Boolean,
    val address: String,
    val data: String,
    val topics: List<TopicResponse>,
) {
    companion object {
        fun Log.toResponse(): LogResponse =
            LogResponse(
                transaction = transaction.toResponse(),
                timestamp = timestamp,
                logIndex = logIndex,
                removed = removed,
                address = address,
                data = data,
                topics = topics.map { it.toResponse() },
            )
    }
}
