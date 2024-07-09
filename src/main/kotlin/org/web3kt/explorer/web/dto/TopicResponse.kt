package org.web3kt.explorer.web.dto

import org.web3kt.explorer.domain.topic.Topic

data class TopicResponse(
    val value: String,
    val name: String?,
) {
    companion object {
        fun Topic.toResponse(): TopicResponse = TopicResponse(value, name)
    }
}
