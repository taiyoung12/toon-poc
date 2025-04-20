package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.code.ToonCode
import com.comics.lezhin.toon.poc.common.enums.toon.Genre
import com.comics.lezhin.toon.poc.common.enums.toon.PriceType
import com.comics.lezhin.toon.poc.common.enums.toon.ScheduleDay
import com.comics.lezhin.toon.poc.common.enums.toon.ToonState
import com.comics.lezhin.toon.poc.entity.ToonEntity
import com.comics.lezhin.toon.poc.repository.ToonRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ToonReaderTest {
    @Mock
    private lateinit var toonRepository: ToonRepository

    private lateinit var sut: ToonReader

    @BeforeEach
    fun setUp() {
        sut =
            ToonReader(
                toonRepository = toonRepository,
            )
    }

    @Test
    fun `ID에 해당하는 웹툰이 있으면 조회할 수 있다`() {
        val toonId = 1L
        val toon =
            ToonEntity(
                title = "우리사이느은",
                isAdultOnly = false,
                price = 300,
                priceType = PriceType.PAID,
                toonState = ToonState.COMPLETED,
                genre = Genre.ROMANCE,
                scheduleDay = ScheduleDay.MONDAY,
            )

        `when`(toonRepository.findById(id = toonId)).thenReturn(toon)

        sut.getToonBy(id = toonId)

        verify(toonRepository, times(1)).findById(id = toonId)
    }

    @Test
    fun `ID에 해당하는 웹툰이 없으면 예외가 발생할 수 있다`() {
        val toonId = 999L

        `when`(toonRepository.findById(id = toonId)).thenReturn(null)

        val actual =
            assertThrows<BaseException> {
                sut.getToonBy(id = toonId)
            }

        assertEquals(ToonCode.NOT_FOUND_TOON_BY_ID, actual.code)
        verify(toonRepository, times(1)).findById(id = toonId)
    }
}
