package org.web3kt.explorer.web

import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.web3kt.explorer.service.TokenService
import org.web3kt.explorer.service.TokenTransactionService
import org.web3kt.explorer.web.dto.TokenDetailResponse
import org.web3kt.explorer.web.dto.TokenTransactionResponse

@RestController
@RequestMapping("v1/tokens")
class TokenController(
    private val tokenService: TokenService,
    private val tokenTransactionService: TokenTransactionService,
) {
    @GetMapping("{token}")
    fun readOne(
        @PathVariable token: String,
    ): TokenDetailResponse = tokenService.readOne(token)

    @GetMapping("{token}/token-transactions")
    fun readTokenTransactionsByToken(
        @PathVariable token: String,
        @ParameterObject pageable: Pageable,
    ): PagedModel<TokenTransactionResponse> = tokenTransactionService.readAllByToken(token, pageable)
}
