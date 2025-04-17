package com.comics.lezhin.toon.poc.entity

import com.comics.lezhin.toon.poc.enums.toon.Genre
import com.comics.lezhin.toon.poc.enums.toon.PriceType
import com.comics.lezhin.toon.poc.enums.toon.ScheduleDay
import com.comics.lezhin.toon.poc.enums.toon.ToonState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ToonEntityTest {
    @Test
    fun `ToonEntity를 생성할 수 있다`() {
        val title = "브리아노의 연구소"
        val isAdultOnly = false
        val price = 1000
        val priceType = PriceType.PAID
        val toonState = ToonState.SCHEDULED
        val genre = Genre.ACTION
        val scheduleDay = ScheduleDay.MONDAY

        val actual =
            ToonEntity(
                title = title,
                isAdultOnly = isAdultOnly,
                price = price,
                priceType = priceType,
                toonState = toonState,
                genre = genre,
                scheduleDay = scheduleDay,
            )

        assertEquals(title, actual.title)
        assertEquals(isAdultOnly, actual.isAdultOnly)
        assertEquals(price, actual.price)
        assertEquals(priceType, actual.priceType)
        assertEquals(toonState, actual.toonState)
        assertEquals(genre, actual.genre)
        assertEquals(scheduleDay, actual.scheduleDay)
    }
}
