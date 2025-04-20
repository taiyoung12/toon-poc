package com.comics.lezhin.toon.poc.inmemory.dto

import com.comics.lezhin.toon.poc.entity.ToonEntity
import org.springframework.data.redis.core.ZSetOperations

data class ToonRankingDto(
    val toon: ToonEntity,
    val score: Double,
)

fun ZSetOperations.TypedTuple<Any>.toRankingItem(): ToonRankingDto? {
    val toon = this.value as? ToonEntity ?: return null
    val score = this.score ?: return null
    return ToonRankingDto(toon, score)
}
