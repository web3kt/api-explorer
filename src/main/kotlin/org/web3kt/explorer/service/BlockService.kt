package org.web3kt.explorer.service

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import org.web3kt.explorer.domain.block.BlockRepository
import org.web3kt.explorer.internal.toPagedModel
import org.web3kt.explorer.web.dto.BlockDetailResponse
import org.web3kt.explorer.web.dto.BlockDetailResponse.Companion.toDetailResponse
import org.web3kt.explorer.web.dto.BlockResponse
import org.web3kt.explorer.web.dto.BlockResponse.Companion.toResponse
import java.math.BigInteger

@Service
class BlockService(
    private val blockRepository: BlockRepository,
) {
    fun readAll(pageable: Pageable): PagedModel<BlockResponse> =
        blockRepository
            .findAll(pageable)
            .map { it.toResponse() }
            .toPagedModel()

    fun readOne(number: BigInteger): BlockDetailResponse = blockRepository.findById(number).orElseThrow().toDetailResponse()
}
