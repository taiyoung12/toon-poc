package com.comics.lezhin.toon.poc.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ToonPurchaseEntityTest {
    @Test
    fun `ToonPurchaseEntity를 생성할 수 있다`() {
        val userId = 1L
        val toonId = 1L
        val purchasedAt = LocalDateTime.now()
        val price = 300

        val actual =
            ToonPurchaseEntity(
                userId = userId,
                toonId = toonId,
                purchasedAt = purchasedAt,
                price = price,
            )

        assertEquals(userId, actual.userId)
        assertEquals(toonId, actual.toonId)
        assertEquals(purchasedAt, actual.purchasedAt)
        assertEquals(price, actual.price)
    }
}
