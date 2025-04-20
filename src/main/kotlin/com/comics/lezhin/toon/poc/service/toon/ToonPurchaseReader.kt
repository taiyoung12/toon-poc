package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.inmemory.PurchaseRepository
import com.comics.lezhin.toon.poc.inmemory.dto.ToonPurchaseDto
import org.springframework.stereotype.Service

@Service
class ToonPurchaseReader(
    private val purchaseRepository: PurchaseRepository,
) {
    fun findPurchaseTop10(): List<ToonPurchaseDto> = purchaseRepository.getPurchaseTop10()
}
