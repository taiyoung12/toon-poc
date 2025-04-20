package com.comics.lezhin.toon.poc.service.fixtures

import com.comics.lezhin.toon.poc.common.enums.toon.Genre
import com.comics.lezhin.toon.poc.common.enums.toon.PriceType
import com.comics.lezhin.toon.poc.common.enums.toon.ScheduleDay
import com.comics.lezhin.toon.poc.common.enums.toon.ToonState
import com.comics.lezhin.toon.poc.entity.ToonEntity
import com.comics.lezhin.toon.poc.inmemory.dto.ToonRankingDto

object ToonFixture {
    fun createMockToonRankings(isAdultOnly: Boolean): List<ToonRankingDto> {
        val rankings = mutableListOf<ToonRankingDto>()

        for (i in 1..10) {
            val toon =
                ToonEntity(
                    title = "웹툰 $i",
                    isAdultOnly = isAdultOnly,
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
