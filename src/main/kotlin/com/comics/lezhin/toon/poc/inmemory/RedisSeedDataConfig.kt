package com.comics.lezhin.toon.poc.inmemory

import com.comics.lezhin.toon.poc.entity.ToonEntity
import com.comics.lezhin.toon.poc.inmemory.dto.ToonDto
import com.comics.lezhin.toon.poc.repository.ToonRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.core.RedisTemplate

@Configuration
@Profile("local")
class RedisSeedDataConfig(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val toonRepository: ToonRepository,
    @Qualifier("redisObjectMapper")
    private val objectMapper: ObjectMapper,
) : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        if (redisTemplate.hasKey(RedisKeys.VIEWED_TOP_GENERAL) ||
            redisTemplate.hasKey(RedisKeys.VIEWED_TOP_ADULT)
        ) {
            println("Redis 시드 데이터 이미 존재")
            return
        }

        val toonsFromDB = toonRepository.findAll()
        val generalToons = toonsFromDB.filter { !it.isAdultOnly }
        val adultToons = toonsFromDB.filter { it.isAdultOnly }

        val generalZSet = redisTemplate.opsForZSet()
        generalToons.forEachIndexed { index, toon ->
            val viewCount = 980.0 - (index * 30.0)
            val toonDto = createToonDtoFromEntity(toon)
            val json = objectMapper.writeValueAsString(toonDto)
            generalZSet.add(RedisKeys.VIEWED_TOP_GENERAL, json, viewCount)
            setViewCount(toonDto, viewCount)
        }

        val adultZSet = redisTemplate.opsForZSet()
        adultToons.forEachIndexed { index, toon ->
            val viewCount = 950.0 - (index * 30.0)
            val toonDto = createToonDtoFromEntity(toon)
            val json = objectMapper.writeValueAsString(toonDto)
            adultZSet.add(RedisKeys.VIEWED_TOP_ADULT, json, viewCount)
            setViewCount(toonDto, viewCount)
        }

        val purchaseZSet = redisTemplate.opsForZSet()
        (generalToons + adultToons).forEach { toon ->
            val toonDto = createToonDtoFromEntity(toon)
            val purchaseCount = (100..1000).random().toDouble()
            val json = objectMapper.writeValueAsString(toonDto)
            purchaseZSet.add(RedisKeys.PURCHASE_RANK_KEY, json, purchaseCount)
            setPurchaseCount(toonDto, purchaseCount)
        }

        println("Redis 시드 데이터 초기화 완료")
    }

    private fun createToonDtoFromEntity(entity: ToonEntity): ToonDto =
        ToonDto(
            id = entity.getId(),
            title = entity.title,
            adultOnly = entity.isAdultOnly,
            price = entity.price,
            priceType = entity.priceType,
            toonState = entity.toonState,
            genre = entity.genre,
            scheduleDay = entity.scheduleDay,
        )

    private val viewCountMap = mutableMapOf<ToonDto, Double>()
    private val purchaseCountMap = mutableMapOf<ToonDto, Double>()

    private fun setViewCount(
        toon: ToonDto,
        count: Double,
    ) {
        viewCountMap[toon] = count
    }

    private fun setPurchaseCount(
        toon: ToonDto,
        count: Double,
    ) {
        purchaseCountMap[toon] = count
    }
}
