package com.comics.lezhin.toon.poc.inmemory

import com.comics.lezhin.toon.poc.inmemory.RedisKeys.PURCHASE_RANK_KEY
import com.comics.lezhin.toon.poc.inmemory.dto.ToonPurchaseDto
import com.comics.lezhin.toon.poc.inmemory.dto.toPurchaseDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PurchaseRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
    @Qualifier("redisObjectMapper")
    private val objectMapper: ObjectMapper,
) {
    fun incrementPurchase(toonId: Long) {
        redisTemplate.opsForZSet().incrementScore(PURCHASE_RANK_KEY, toonId.toString(), 1.0)
    }

    fun getPurchaseTop10(): List<ToonPurchaseDto> {
        val zSetOps = redisTemplate.opsForZSet()
        val top10 = zSetOps.reverseRangeWithScores(PURCHASE_RANK_KEY, 0, 9) ?: return emptyList()
        return top10.mapNotNull { it.toPurchaseDto(objectMapper = objectMapper) }
    }
}
