package org.web3kt.explorer.service

import kotlinx.coroutines.runBlocking
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import org.web3kt.abi.TypeDecoder
import org.web3kt.abi.type.StringType
import org.web3kt.abi.type.Uint256Type
import org.web3kt.abi.type.Uint8Type
import org.web3kt.core.Web3
import org.web3kt.core.protocol.dto.Tag
import org.web3kt.core.protocol.dto.TransactionCall
import org.web3kt.explorer.domain.token.Token
import org.web3kt.explorer.domain.token.TokenRepository
import org.web3kt.explorer.internal.toPagedModel
import org.web3kt.explorer.web.dto.TokenDetailResponse
import org.web3kt.explorer.web.dto.TokenResponse
import org.web3kt.explorer.web.dto.TokenResponse.Companion.toResponse
import java.math.BigInteger

@OptIn(ExperimentalStdlibApi::class)
@Service
class TokenService(
    private val web3: Web3,
    private val tokenRepository: TokenRepository,
) {
    fun readOne(token: String): TokenDetailResponse =
        TokenDetailResponse(token, getName(token), getSymbol(token), getDecimals(token), getTotalSupply(token))

    fun findById(token: String): Token =
        tokenRepository.findByIdOrNull(token)
            ?: tokenRepository.save(Token(token, getName(token), getSymbol(token), getDecimals(token)))

    private fun getName(token: String): String? =
        try {
            TypeDecoder.decode(call(token, SELECTOR_NAME).drop(32).toByteArray(), StringType::class).value
        } catch (e: Exception) {
            null
        }

    private fun getSymbol(token: String): String? =
        try {
            TypeDecoder.decode(call(token, SELECTOR_SYMBOL).drop(32).toByteArray(), StringType::class).value
        } catch (e: Exception) {
            null
        }

    private fun getDecimals(token: String): Int? =
        try {
            TypeDecoder.decode(call(token, SELECTOR_DECIMALS), Uint8Type::class).value.toInt()
        } catch (e: Exception) {
            null
        }

    private fun getTotalSupply(token: String): BigInteger? =
        try {
            TypeDecoder.decode(call(token, SELECTOR_TOTAL_SUPPLY), Uint256Type::class).value
        } catch (e: Exception) {
            null
        }

    private fun call(
        token: String,
        selector: String,
    ): ByteArray =
        runBlocking {
            web3.eth.call(TransactionCall(to = token, input = selector), Tag.LATEST)
        }.removePrefix("0x").hexToByteArray()

    fun readAll(pageable: Pageable): PagedModel<TokenResponse> = tokenRepository.findAll(pageable).map { it.toResponse() }.toPagedModel()

    companion object {
        private const val SELECTOR_NAME = "0x06fdde03"
        private const val SELECTOR_SYMBOL = "0x95d89b41"
        private const val SELECTOR_DECIMALS = "0x313ce567"
        private const val SELECTOR_TOTAL_SUPPLY = "0x18160ddd"
    }
}
