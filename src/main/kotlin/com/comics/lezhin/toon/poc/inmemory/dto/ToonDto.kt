package com.comics.lezhin.toon.poc.inmemory.dto

import com.comics.lezhin.toon.poc.common.enums.toon.Genre
import com.comics.lezhin.toon.poc.common.enums.toon.PriceType
import com.comics.lezhin.toon.poc.common.enums.toon.ScheduleDay
import com.comics.lezhin.toon.poc.common.enums.toon.ToonState

data class ToonDto(
    val title: String,
    val adultOnly: Boolean,
    val price: Int,
    val priceType: PriceType,
    val toonState: ToonState,
    val genre: Genre,
    val scheduleDay: ScheduleDay,
)
