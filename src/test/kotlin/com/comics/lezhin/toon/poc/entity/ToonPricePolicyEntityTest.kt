package com.comics.lezhin.toon.poc.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ToonPricePolicyEntityTest {
    @Test
    fun `ToonPricePolicy를 생성할 수 있다`() {
        val toonId = 1L
        val startDate = LocalDate.now()
        val endDate = LocalDate.now()
        val price = 300

        val actual =
            ToonPricePolicyEntity(
                toonId = toonId,
                startDate = startDate,
                endDate = endDate,
                price = price,
            )

        assertEquals(toonId, actual.toonId)
        assertEquals(startDate, actual.startDate)
        assertEquals(endDate, actual.endDate)
        assertEquals(price, actual.price)
    }
}
