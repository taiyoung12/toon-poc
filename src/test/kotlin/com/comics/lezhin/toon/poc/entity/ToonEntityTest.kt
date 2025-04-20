package com.comics.lezhin.toon.poc.entity

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.enums.toon.Genre
import com.comics.lezhin.toon.poc.common.enums.toon.PriceType
import com.comics.lezhin.toon.poc.common.enums.toon.ScheduleDay
import com.comics.lezhin.toon.poc.common.enums.toon.ToonState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun `미성년자를 필터할 수 있다`() {
        val userEntity =
            UserEntity(
                email = "lezhin1@test.com",
                password = "lezhin123!",
                name = "한우진",
                age = 19,
            )

        val title = "브리아노의 연구소"
        val isAdultOnly = true
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

        assertThrows<BaseException> {
            actual.filter(userEntity)
        }
    }

    @Test
    fun `성인을 필터할 수 있다`() {
        val userEntity =
            UserEntity(
                email = "lezhin1@test.com",
                password = "lezhin123!",
                name = "한우진",
                age = 20,
            )

        val title = "브리아노의 연구소"
        val isAdultOnly = true
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

        assertDoesNotThrow {
            actual.filter(userEntity)
        }
    }
}
