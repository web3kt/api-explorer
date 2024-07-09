package org.web3kt.explorer.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.web3kt.core.Web3
import org.web3kt.core.protocol.dto.Tag
import org.web3kt.explorer.internal.isContract
import org.web3kt.explorer.web.dto.AddressDetailResponse

@Service
class AddressService(
    private val web3: Web3,
) {
    fun readOne(address: String): AddressDetailResponse =
        AddressDetailResponse(
            id = address,
            balance = runBlocking { web3.eth.getBalance(address, Tag.LATEST) },
            isContract = runBlocking { web3.isContract(address) },
        )
}
