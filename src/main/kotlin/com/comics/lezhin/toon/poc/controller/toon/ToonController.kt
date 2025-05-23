package com.comics.lezhin.toon.poc.controller.toon

import com.comics.lezhin.toon.poc.app.annotation.UserId
import com.comics.lezhin.toon.poc.app.response.Response
import com.comics.lezhin.toon.poc.application.toon.ToonApplication
import com.comics.lezhin.toon.poc.application.toon.ToonPurchaseApplication
import com.comics.lezhin.toon.poc.application.toon.ToonViewApplication
import com.comics.lezhin.toon.poc.common.code.ToonCode
import com.comics.lezhin.toon.poc.common.code.ToonViewCode
import com.comics.lezhin.toon.poc.controller.response.PurchaseToonResponse
import com.comics.lezhin.toon.poc.controller.response.ReadToonPurchaseResponse
import com.comics.lezhin.toon.poc.controller.response.ReadToonRankResponse
import com.comics.lezhin.toon.poc.controller.response.ReadToonViewHistoryResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/toon")
class ToonController(
    private val toonViewApplication: ToonViewApplication,
    private val toonApplication: ToonApplication,
    private val toonPurchaseApplication: ToonPurchaseApplication,
) {
    @GetMapping("/{toonId}/viewed")
    fun readToonViewHistory(
        @PathVariable("toonId") toonId: Long,
    ): Response<ReadToonViewHistoryResponse?> {
        val response = toonViewApplication.readToonViewHistory(id = toonId)
        return Response.success(ToonViewCode.SUCCESS, response)
    }

    @PostMapping("/{toonId}/purchase")
    fun purchaseToon(
        @PathVariable("toonId") toonId: Long,
        @UserId userId: Long,
    ): Response<PurchaseToonResponse> {
        val response = toonApplication.purchase(userId = userId, toonId = toonId)
        return Response.success(ToonCode.TOON_PURCHASE_SUCCESS, PurchaseToonResponse(response))
    }

    @GetMapping("/popular/rank")
    fun readPopularToon(
        @UserId userId: Long,
    ): Response<ReadToonRankResponse> {
        val response = toonViewApplication.readTop10(userId = userId)
        return Response.success(ToonCode.SUCCESS_READ_POPULAR_TOON, response)
    }

    @GetMapping("/purchase/rank")
    fun readPurchaseToon(
        @UserId userId: Long,
    ): Response<ReadToonPurchaseResponse> {
        val response = toonPurchaseApplication.readTop10()
        return Response.success(ToonCode.SUCCESS_READ_PURCHASE_TOON, response)
    }

    @DeleteMapping("/{toonId}")
    fun deleteToonAllInfo(
        @PathVariable("toonId") toonId: Long,
    ): Response<Void?> {
        val deletedAt = LocalDateTime.now()
        toonApplication.deleteToonAllInfo(
            deletedAt = deletedAt,
            toonId = toonId,
        )
        return Response.success(ToonCode.SUCCESS_DELETE_TOON_INFO)
    }
}
