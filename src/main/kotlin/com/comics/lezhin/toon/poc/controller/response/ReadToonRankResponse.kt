package com.comics.lezhin.toon.poc.controller.response

import com.comics.lezhin.toon.poc.inmemory.dto.ToonRankingDto

data class ReadToonRankResponse(
    val rankings: List<ToonRankDto>,
)

data class ToonRankDto(
    val title: String,
    val rank: Int,
)

fun List<ToonRankingDto>.toResponse(): ReadToonRankResponse {
    val rankingItems =
        this.mapIndexed { index, dto ->
            ToonRankDto(
                title = dto.toon.title,
                rank = index + 1,
            )
        }

    return ReadToonRankResponse(rankingItems)
}
