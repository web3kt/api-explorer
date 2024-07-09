package org.web3kt.explorer.web

import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.web3kt.explorer.service.BlockService
import org.web3kt.explorer.web.dto.BlockDetailResponse
import org.web3kt.explorer.web.dto.BlockResponse
import java.math.BigInteger

@RestController
@RequestMapping("v1/blocks")
class BlockController(
    private val blockService: BlockService,
) {
    @GetMapping
    fun readAll(
        @ParameterObject pageable: Pageable,
    ): PagedModel<BlockResponse> = blockService.readAll(pageable)

    @GetMapping("{number}")
    fun readOne(
        @PathVariable number: BigInteger,
    ): BlockDetailResponse = blockService.readOne(number)
}
