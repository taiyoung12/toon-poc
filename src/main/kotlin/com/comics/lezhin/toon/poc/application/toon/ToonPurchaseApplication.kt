package com.comics.lezhin.toon.poc.application.toon

import com.comics.lezhin.toon.poc.app.annotation.ApplicationLayer
import com.comics.lezhin.toon.poc.controller.response.ReadToonPurchaseResponse
import com.comics.lezhin.toon.poc.controller.response.toResponse
import com.comics.lezhin.toon.poc.service.toon.ToonPurchaseReader

@ApplicationLayer
class ToonPurchaseApplication(
    private val toonPurchaseReader: ToonPurchaseReader,
) {
    fun readTop10(): ReadToonPurchaseResponse {
        val toonPurchaseDtoList = toonPurchaseReader.findPurchaseTop10()
        return toonPurchaseDtoList.toResponse()
    }
}
