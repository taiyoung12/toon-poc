package com.comics.lezhin.toon.poc.controller.toon

import com.comics.lezhin.toon.poc.app.response.Response
import com.comics.lezhin.toon.poc.application.toon.ToonViewApplication
import com.comics.lezhin.toon.poc.common.code.ToonViewCode
import com.comics.lezhin.toon.poc.controller.response.ReadToonViewHistoryResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/toon")
class ToonController(
    private val toonViewApplication: ToonViewApplication,
) {
    @GetMapping("/{toonId}/viewed")
    fun readToonViewHistory(
        @PathVariable("toonId") toonId: Long,
    ): Response<ReadToonViewHistoryResponse?> {
        val response = toonViewApplication.readToonViewHistory(id = toonId)
        return Response.success(ToonViewCode.SUCCESS, response)
    }
}
