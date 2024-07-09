package org.web3kt.explorer.service

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import org.web3kt.explorer.domain.internalTransaction.InternalTransactionRepository
import org.web3kt.explorer.internal.toPagedModel
import org.web3kt.explorer.web.dto.InternalTransactionResponse
import org.web3kt.explorer.web.dto.InternalTransactionResponse.Companion.toResponse

@Service
class InternalTransactionService(
    private val internalTransactionRepository: InternalTransactionRepository,
) {
    fun readAllByAddress(
        address: String,
        pageable: Pageable,
    ): PagedModel<InternalTransactionResponse> =
        internalTransactionRepository
            .findByAddress(address, pageable)
            .map { it.toResponse() }
            .toPagedModel()
}
