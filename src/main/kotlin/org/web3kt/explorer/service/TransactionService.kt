package org.web3kt.explorer.service

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import org.web3kt.explorer.domain.transaction.QTransaction
import org.web3kt.explorer.domain.transaction.TransactionRepository
import org.web3kt.explorer.internal.filter
import org.web3kt.explorer.internal.findAll
import org.web3kt.explorer.internal.toPagedModel
import org.web3kt.explorer.web.dto.TransactionDetailResponse
import org.web3kt.explorer.web.dto.TransactionDetailResponse.Companion.toDetailResponse
import org.web3kt.explorer.web.dto.TransactionResponse
import org.web3kt.explorer.web.dto.TransactionResponse.Companion.toResponse
import java.math.BigInteger

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
) {
    fun readAll(
        blockId: BigInteger?,
        pageable: Pageable,
    ): PagedModel<TransactionResponse> =
        transactionRepository
            .findAll(pageable) {
                filter(blockId) {
                    QTransaction.transaction.block.id
                        .eq(it)
                }
            }.map { it.toResponse() }
            .toPagedModel()

    fun readOne(id: String): TransactionDetailResponse =
        transactionRepository
            .findById(id)
            .orElseThrow()
            .toDetailResponse()

    fun readAllByAddress(
        address: String,
        pageable: Pageable,
    ): PagedModel<TransactionResponse> =
        transactionRepository
            .findByAddress(address, pageable)
            .map { it.toResponse() }
            .toPagedModel()
}
