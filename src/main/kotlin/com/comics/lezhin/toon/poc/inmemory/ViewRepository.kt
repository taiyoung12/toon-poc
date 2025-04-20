package com.comics.lezhin.toon.poc.inmemory

import com.comics.lezhin.toon.poc.inmemory.dto.ToonRankingDto
import com.comics.lezhin.toon.poc.inmemory.dto.toRankingItem
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class ViewRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    private val objectMapper =
        ObjectMapper()
            .registerKotlinModule()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    fun getAdultTop10(): List<ToonRankingDto> {
        ensureCombinedKeyExists()

        val zSetOps = redisTemplate.opsForZSet()
        val all =
            zSetOps.reverseRangeWithScores(RedisKeys.VIEWED_TOP_COMBINED, 0, 9)
                ?: return emptyList()

        return all.mapNotNull { it.toRankingItem(objectMapper = objectMapper) }
    }

    fun getGeneralTop10(): List<ToonRankingDto> {
        val zSetOps = redisTemplate.opsForZSet()
        val generalTop10 =
            zSetOps.reverseRangeWithScores(RedisKeys.VIEWED_TOP_GENERAL, 0, 9)
                ?: return emptyList()

        return generalTop10.mapNotNull { it.toRankingItem(objectMapper = objectMapper) }
    }

    private fun ensureCombinedKeyExists() {
        if (!redisTemplate.hasKey(RedisKeys.VIEWED_TOP_COMBINED)) {
            val zSetOps = redisTemplate.opsForZSet()
            zSetOps.unionAndStore(
                RedisKeys.VIEWED_TOP_GENERAL,
                RedisKeys.VIEWED_TOP_ADULT,
                RedisKeys.VIEWED_TOP_COMBINED,
            )
        }
    }
}
