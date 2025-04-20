package com.comics.lezhin.toon.poc.inmemory

import com.comics.lezhin.toon.poc.common.enums.toon.Genre
import com.comics.lezhin.toon.poc.common.enums.toon.PriceType
import com.comics.lezhin.toon.poc.common.enums.toon.ScheduleDay
import com.comics.lezhin.toon.poc.common.enums.toon.ToonState
import com.comics.lezhin.toon.poc.inmemory.dto.ToonDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.core.RedisTemplate

@Configuration
@Profile("local")
class RedisSeedDataConfig(
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    private val mapper = ObjectMapper()

    @PostConstruct
    fun init() {
        if (redisTemplate.hasKey(RedisKeys.VIEWED_TOP_GENERAL) ||
            redisTemplate.hasKey(RedisKeys.VIEWED_TOP_ADULT)
        ) {
            return
        }

        val generalToons =
            listOf(
                createToon("연애혁명", false, 0, PriceType.FREE, Genre.ROMANCE, ScheduleDay.MONDAY, 980.0),
                createToon("어쩌다 발견한 7월", false, 3, PriceType.PAID, Genre.ROMANCE, ScheduleDay.TUESDAY, 920.0),
                createToon("나쁜 쪽으로", false, 5, PriceType.PAID, Genre.ACTION, ScheduleDay.WEDNESDAY, 890.0),
                createToon("그 남자의 거짓말", false, 3, PriceType.PAID, Genre.ROMANCE, ScheduleDay.THURSDAY, 860.0),
                createToon("우리사이느은", false, 0, PriceType.FREE, Genre.ROMANCE, ScheduleDay.FRIDAY, 830.0),
                createToon("합법적인 왕따", false, 5, PriceType.PAID, Genre.ACTION, ScheduleDay.SATURDAY, 800.0),
                createToon("호랑이형님", false, 0, PriceType.FREE, Genre.ACTION, ScheduleDay.SUNDAY, 780.0),
                createToon("지금 우리 학교는", false, 3, PriceType.PAID, Genre.ROMANCE, ScheduleDay.MONDAY, 750.0),
                createToon("대학원 탈출일지", false, 0, PriceType.FREE, Genre.ACTION, ScheduleDay.TUESDAY, 730.0),
                createToon("신의 탑", false, 5, PriceType.PAID, Genre.ACTION, ScheduleDay.WEDNESDAY, 710.0),
                createToon("몬스터", false, 3, PriceType.PAID, Genre.ACTION, ScheduleDay.THURSDAY, 690.0),
                createToon("위대한 소머즈", false, 0, PriceType.FREE, Genre.ROMANCE, ScheduleDay.FRIDAY, 670.0),
            )

        val adultToons =
            listOf(
                createToon("은밀하게 위대하게", true, 7, PriceType.PAID, Genre.ACTION, ScheduleDay.MONDAY, 950.0),
                createToon("스위트홈", true, 5, PriceType.PAID, Genre.ACTION, ScheduleDay.TUESDAY, 900.0),
                createToon("킬링스토킹", true, 7, PriceType.PAID, Genre.ACTION, ScheduleDay.WEDNESDAY, 870.0),
                createToon("체인소맨", true, 5, PriceType.PAID, Genre.ACTION, ScheduleDay.THURSDAY, 850.0),
                createToon("자신감", true, 7, PriceType.PAID, Genre.ROMANCE, ScheduleDay.FRIDAY, 820.0),
                createToon("조교사", true, 5, PriceType.PAID, Genre.ACTION, ScheduleDay.SATURDAY, 790.0),
                createToon("완벽한 관계", true, 7, PriceType.PAID, Genre.ROMANCE, ScheduleDay.SUNDAY, 760.0),
                createToon("해피 슈가 라이프", true, 5, PriceType.PAID, Genre.ACTION, ScheduleDay.MONDAY, 740.0),
            )

        val generalZSet = redisTemplate.opsForZSet()
        generalToons.forEach { toon ->
            val toonDto = mapper.writeValueAsString(toon)
            generalZSet.add(RedisKeys.VIEWED_TOP_GENERAL, toonDto, getViewCount(toon))
        }

        val adultZSet = redisTemplate.opsForZSet()
        adultToons.forEach { toon ->
            val toonDto = mapper.writeValueAsString(toon)
            adultZSet.add(RedisKeys.VIEWED_TOP_ADULT, toonDto, getViewCount(toon))
        }

        val purchaseZSet = redisTemplate.opsForZSet()
        (generalToons + adultToons).forEach { toon ->
            val json = mapper.writeValueAsString(toon)
            purchaseZSet.add(RedisKeys.PURCHASE_RANK_KEY, json, getPurchaseCount(toon))
        }

        println("Redis 시드 데이터 초기화 완료")
    }

    private fun createToon(
        title: String,
        isAdultOnly: Boolean,
        price: Int,
        priceType: PriceType,
        genre: Genre,
        scheduleDay: ScheduleDay,
        viewCount: Double,
        purchaseCount: Double = (100..1000).random().toDouble(),
    ): ToonDto {
        val toon =
            ToonDto(
                title = title,
                adultOnly = isAdultOnly,
                price = price,
                priceType = priceType,
                toonState = ToonState.SCHEDULED,
                genre = genre,
                scheduleDay = scheduleDay,
            )

        setViewCount(toon, viewCount)
        setPurchaseCount(toon, purchaseCount)

        return toon
    }

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

    private fun getViewCount(toon: ToonDto): Double = viewCountMap[toon] ?: 0.0

    private fun getPurchaseCount(toon: ToonDto): Double = purchaseCountMap[toon] ?: 0.0
}
