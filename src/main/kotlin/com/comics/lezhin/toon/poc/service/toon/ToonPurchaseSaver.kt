package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.entity.ToonPurchaseEntity
import com.comics.lezhin.toon.poc.repository.ToonPurchaseRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ToonPurchaseSaver(
    private val toonPurchaseRepository: ToonPurchaseRepository,
) {
    fun save(
        userId: Long,
        toonId: Long,
        deductBalance: Int,
    ) {
        val toonPurchaseEntity =
            ToonPurchaseEntity(
                userId = userId,
                toonId = toonId,
                purchasedAt = LocalDateTime.now(),
                price = deductBalance,
            )

        toonPurchaseRepository.save(toonPurchaseEntity = toonPurchaseEntity)
    }
}
