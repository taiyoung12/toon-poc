package com.comics.lezhin.toon.poc.inmemory

import com.comics.lezhin.toon.poc.inmemory.RedisKeys.PURCHASE_RANK_KEY
import com.comics.lezhin.toon.poc.inmemory.dto.ToonDto
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
        val zSetOps = redisTemplate.opsForZSet()
        val all = zSetOps.range(PURCHASE_RANK_KEY, 0, -1) ?: return

        val targetJson =
            all.firstOrNull {
                try {
                    val dto = objectMapper.readValue(it.toString(), ToonDto::class.java)
                    dto.id == toonId
                } catch (e: Exception) {
                    false
                }
            } ?: return

        zSetOps.incrementScore(PURCHASE_RANK_KEY, targetJson, 1.0)
    }

    fun getPurchaseTop10(): List<ToonPurchaseDto> {
        val zSetOps = redisTemplate.opsForZSet()
        val top10 = zSetOps.reverseRangeWithScores(PURCHASE_RANK_KEY, 0, 9) ?: return emptyList()
        return top10.mapNotNull { it.toPurchaseDto(objectMapper = objectMapper) }
    }
}
