package org.web3kt.explorer.web

import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.web3kt.explorer.service.AddressService
import org.web3kt.explorer.service.InternalTransactionService
import org.web3kt.explorer.service.LogService
import org.web3kt.explorer.service.TokenTransactionService
import org.web3kt.explorer.service.TransactionService
import org.web3kt.explorer.web.dto.AddressDetailResponse
import org.web3kt.explorer.web.dto.InternalTransactionResponse
import org.web3kt.explorer.web.dto.LogResponse
import org.web3kt.explorer.web.dto.TokenTransactionResponse
import org.web3kt.explorer.web.dto.TransactionResponse

@RestController
@RequestMapping("v1/addresses")
class AddressController(
    private val addressService: AddressService,
    private val transactionService: TransactionService,
    private val internalTransactionService: InternalTransactionService,
    private val logService: LogService,
    private val tokenTransactionService: TokenTransactionService,
) {
    @GetMapping("{address}")
    fun readOne(
        @PathVariable address: String,
    ): AddressDetailResponse = addressService.readOne(address)

    @GetMapping("{address}/transactions")
    fun readTransactionsByAddress(
        @PathVariable address: String,
        @ParameterObject pageable: Pageable,
    ): PagedModel<TransactionResponse> = transactionService.readAllByAddress(address, pageable)

    @GetMapping("{address}/internal-transactions")
    fun readInternalTransactionsByAddress(
        @PathVariable address: String,
        @ParameterObject pageable: Pageable,
    ): PagedModel<InternalTransactionResponse> = internalTransactionService.readAllByAddress(address, pageable)

    @GetMapping("{address}/token-transactions")
    fun readTokenTransactionsByToken(
        @PathVariable address: String,
        @ParameterObject pageable: Pageable,
    ): PagedModel<TokenTransactionResponse> = tokenTransactionService.readAllByAddress(address, pageable)

    @GetMapping("{address}/logs")
    fun readAll(
        @PathVariable address: String,
        @ParameterObject pageable: Pageable,
    ): PagedModel<LogResponse> = logService.readAll(address, pageable)
}
