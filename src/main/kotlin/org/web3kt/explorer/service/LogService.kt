package org.web3kt.explorer.service

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.web3kt.explorer.domain.log.LogRepository
import org.web3kt.explorer.domain.log.QLog
import org.web3kt.explorer.internal.filter
import org.web3kt.explorer.internal.findAll
import org.web3kt.explorer.internal.toPagedModel
import org.web3kt.explorer.web.dto.LogResponse
import org.web3kt.explorer.web.dto.LogResponse.Companion.toResponse

@Service
@Transactional(readOnly = true)
class LogService(
    private val logRepository: LogRepository,
) {
    fun readAll(
        address: String?,
        pageable: Pageable,
    ): PagedModel<LogResponse> =
        logRepository
            .findAll(pageable) {
                filter(address) { QLog.log.address.eq(it) }
            }.map { it.toResponse() }
            .toPagedModel()
}
