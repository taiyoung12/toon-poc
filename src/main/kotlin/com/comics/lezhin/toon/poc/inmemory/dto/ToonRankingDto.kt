package com.comics.lezhin.toon.poc.inmemory.dto

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.ZSetOperations

data class ToonRankingDto(
    val toon: ToonDto,
    val score: Double,
)

fun ZSetOperations.TypedTuple<Any>.toRankingItem(objectMapper: ObjectMapper): ToonRankingDto? {
    val jsonString = this.value?.toString() ?: return null
    val toon =
        try {
            objectMapper.readValue(jsonString, ToonDto::class.java)
        } catch (e: Exception) {
            return null
        }
    val score = this.score ?: return null
    return ToonRankingDto(toon, score)
}
