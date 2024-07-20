package org.web3kt.explorer.web

import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.web3kt.explorer.service.LogService
import org.web3kt.explorer.web.dto.LogResponse

@RestController
@RequestMapping("v1/logs")
class LogController(
    private val logService: LogService,
) {
    @GetMapping
    fun readAll(
        @ParameterObject pageable: Pageable,
    ): PagedModel<LogResponse> = logService.readAll(null, pageable)
}
