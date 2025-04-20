package com.comics.lezhin.toon.poc.application.toon.fixture

import com.comics.lezhin.toon.poc.common.enums.toon.Genre
import com.comics.lezhin.toon.poc.common.enums.toon.PriceType
import com.comics.lezhin.toon.poc.common.enums.toon.ScheduleDay
import com.comics.lezhin.toon.poc.common.enums.toon.ToonState
import com.comics.lezhin.toon.poc.entity.ToonEntity
import com.comics.lezhin.toon.poc.entity.ToonPricePolicyEntity
import com.comics.lezhin.toon.poc.entity.UserCoinEntity
import com.comics.lezhin.toon.poc.entity.UserEntity
import com.comics.lezhin.toon.poc.inmemory.dto.ToonDto
import com.comics.lezhin.toon.poc.inmemory.dto.ToonRankingDto
import java.time.LocalDate

object ToonFixture {
    const val userId = 1L
    const val toonId = 1L
    const val toonPrice = 3
    const val eventPrice = 0
    const val userCoinBalance = 30

    val userEntity =
        UserEntity(
            email = "lezhin1@test.com",
            password = "lezhin123!",
            name = "한우진",
            age = 20,
        )

    val minorUserEntity =
        UserEntity(
            email = "lezhin1@test.com",
            password = "lezhin123!",
            name = "한우진",
            age = 19,
        )

    val toonEntity =
        ToonEntity(
            title = "브리아노의 연구소",
            isAdultOnly = true,
            price = toonPrice,
            priceType = PriceType.PAID,
            toonState = ToonState.SCHEDULED,
            genre = Genre.ACTION,
            scheduleDay = ScheduleDay.MONDAY,
        )

    val userCoinEntity =
        UserCoinEntity(
            userId = userId,
            balance = userCoinBalance,
        )

    val toonPricePolicyEntity =
        ToonPricePolicyEntity(
            toonId = toonId,
            startDate = LocalDate.now().minusDays(2),
            endDate = LocalDate.now().plusDays(2),
            price = eventPrice,
        )

    fun createToonRankings(): List<ToonRankingDto> {
        val rankings = mutableListOf<ToonRankingDto>()

        for (i in 1..10) {
            val toon =
                ToonDto(
                    id = i.toLong(),
                    title = "웹툰 $i",
                    adultOnly = false,
                    price = 200 + i * 10,
                    priceType = if (i % 2 == 0) PriceType.PAID else PriceType.FREE,
                    toonState = if (i % 2 == 0) ToonState.COMPLETED else ToonState.SCHEDULED,
                    genre = Genre.entries[i % Genre.entries.size],
                    scheduleDay = ScheduleDay.entries[i % ScheduleDay.entries.size],
                )

            rankings.add(ToonRankingDto(toon, (11 - i) * 100.0))
        }

        return rankings
    }

    fun createGeneralToonRankings(): List<ToonRankingDto> {
        val rankings = mutableListOf<ToonRankingDto>()

        for (i in 1..10) {
            val toon =
                ToonDto(
                    id = i.toLong(),
                    title = "웹툰 $i",
                    adultOnly = if (i % 2 == 0) true else false,
                    price = 200 + i * 10,
                    priceType = if (i % 2 == 0) PriceType.PAID else PriceType.FREE,
                    toonState = if (i % 2 == 0) ToonState.COMPLETED else ToonState.SCHEDULED,
                    genre = Genre.entries[i % Genre.entries.size],
                    scheduleDay = ScheduleDay.entries[i % ScheduleDay.entries.size],
                )

            rankings.add(ToonRankingDto(toon, (11 - i) * 100.0))
        }

        return rankings
    }
}
