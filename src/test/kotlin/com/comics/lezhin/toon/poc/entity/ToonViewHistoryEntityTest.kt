package com.comics.lezhin.toon.poc.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ToonViewHistoryEntityTest {
    @Test
    fun `ToonViewHitoryEntity를 생성할 수 있다`() {
        val userId = 1L
        val toonId = 1L
        val viewedAt = LocalDateTime.now()

        val actual =
            ToonViewHistoryEntity(
                userId = userId,
                toonId = toonId,
                viewedAt = viewedAt,
            )

        assertEquals(userId, actual.userId)
        assertEquals(toonId, actual.toonId)
        assertEquals(viewedAt, actual.viewedAt)
    }
}
