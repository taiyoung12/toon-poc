package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.entity.ToonPricePolicyEntity
import com.comics.lezhin.toon.poc.repository.ToonPricePolicyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class ToonPricePolicyReaderTest {
    @Mock
    private lateinit var toonPricePolicyRepository: ToonPricePolicyRepository

    private lateinit var sut: ToonPricePolicyReader

    @BeforeEach
    fun setUp() {
        sut =
            ToonPricePolicyReader(
                toonPricePolicyRepository = toonPricePolicyRepository,
            )
    }

    @Test
    fun `toonId에 해당하는 가격 정책을 조회할 수 있다`() {
        val toonId = 1L
        val now = LocalDate.now()
        val toonPricePolicyEntity =
            ToonPricePolicyEntity(
                toonId = toonId,
                startDate = now.minusDays(1),
                endDate = now.plusDays(1),
                price = 100,
            )

        `when`(toonPricePolicyRepository.findValidPolicy(toonId, now)).thenReturn(toonPricePolicyEntity)

        val actual = sut.findToonPricePolicyBy(toonId)

        assertThat(actual).isEqualTo(toonPricePolicyEntity)
        verify(toonPricePolicyRepository, times(1)).findValidPolicy(toonId, now)
    }

    @Test
    fun `toonId에 해당하는 가격 정책은 null일 수 있다`() {
        val toonId = 999L
        val now = LocalDate.now()

        `when`(toonPricePolicyRepository.findValidPolicy(toonId, now)).thenReturn(null)

        val actual = sut.findToonPricePolicyBy(toonId)

        assertThat(actual).isNull()
        verify(toonPricePolicyRepository, times(1)).findValidPolicy(toonId, now)
    }
}
