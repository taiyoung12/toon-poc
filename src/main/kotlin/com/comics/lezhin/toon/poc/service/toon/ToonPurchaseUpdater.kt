package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.inmemory.PurchaseRepository
import org.springframework.stereotype.Service

@Service
class ToonPurchaseUpdater(
    private val purchaseRepository: PurchaseRepository,
) {
    fun updatePurchaseCount(toonId: Long) {
        purchaseRepository.incrementPurchase(toonId = toonId)
    }
}
