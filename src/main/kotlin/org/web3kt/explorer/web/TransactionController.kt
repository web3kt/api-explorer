package org.web3kt.explorer.web

import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.web3kt.explorer.service.TransactionService
import org.web3kt.explorer.web.dto.TransactionDetailResponse
import org.web3kt.explorer.web.dto.TransactionResponse
import java.math.BigInteger

@RestController
@RequestMapping("v1/transactions")
class TransactionController(
    private val transactionService: TransactionService,
) {
    @GetMapping
    fun readAll(
        @RequestParam blockId: BigInteger?,
        @ParameterObject pageable: Pageable,
    ): PagedModel<TransactionResponse> = transactionService.readAll(blockId, pageable)

    @GetMapping("{id}")
    fun readOne(
        @PathVariable id: String,
    ): TransactionDetailResponse = transactionService.readOne(id)
}
