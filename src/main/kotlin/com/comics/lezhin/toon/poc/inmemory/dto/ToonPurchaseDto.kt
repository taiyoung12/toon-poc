package com.comics.lezhin.toon.poc.inmemory.dto

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.ZSetOperations

data class ToonPurchaseDto(
    val toon: ToonDto,
    val purchaseCount: Int,
)

fun ZSetOperations.TypedTuple<Any>.toPurchaseDto(objectMapper: ObjectMapper): ToonPurchaseDto? {
    val jsonString = this.value?.toString() ?: return null
    return try {
        val toonDto = objectMapper.readValue(jsonString, ToonDto::class.java)
        val count = this.score?.toInt() ?: return null
        ToonPurchaseDto(toonDto, count)
    } catch (e: Exception) {
        null
    }
}
