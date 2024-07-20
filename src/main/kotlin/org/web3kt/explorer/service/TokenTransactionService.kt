package org.web3kt.explorer.service

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.web3kt.explorer.domain.tokenTransaction.TokenTransactionRepository
import org.web3kt.explorer.internal.toPagedModel
import org.web3kt.explorer.web.dto.TokenTransactionResponse
import org.web3kt.explorer.web.dto.TokenTransactionResponse.Companion.toResponse

@Service
@Transactional(readOnly = true)
class TokenTransactionService(
    private val tokenTransactionRepository: TokenTransactionRepository,
) {
    fun readAllByAddress(
        address: String,
        pageable: Pageable,
    ): PagedModel<TokenTransactionResponse> =
        tokenTransactionRepository.findByAddress(address, pageable).map { it.toResponse() }.toPagedModel()

    fun readAllByToken(
        token: String,
        pageable: Pageable,
    ): PagedModel<TokenTransactionResponse> =
        tokenTransactionRepository
            .findByToken(token, pageable)
            .map {
                it.toResponse()
            }.toPagedModel()
}
