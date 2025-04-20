package com.comics.lezhin.toon.poc.controller.response

import com.comics.lezhin.toon.poc.inmemory.dto.ToonPurchaseDto

data class ReadToonPurchaseResponse(
    val rankings: List<PurchaseToonRankDto>,
)

data class PurchaseToonRankDto(
    val title: String,
    val rank: Int,
)

fun List<ToonPurchaseDto>.toResponse(): ReadToonPurchaseResponse {
    val rankingItems =
        this.mapIndexed { index, dto ->
            PurchaseToonRankDto(
                title = dto.toon.title,
                rank = index + 1,
            )
        }

    return ReadToonPurchaseResponse(rankingItems)
}
