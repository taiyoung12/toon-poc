package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.entity.ToonViewHistoryEntity
import com.comics.lezhin.toon.poc.repository.ToonViewHistoryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ToonViewHistoryReaderTest {
    @Mock
    private lateinit var toonViewHistoryRepository: ToonViewHistoryRepository

    private lateinit var sut: ToonViewHistoryReader

    @BeforeEach
    fun setUp() {
        sut =
            ToonViewHistoryReader(
                toonViewHistoryRepository = toonViewHistoryRepository,
            )
    }

    @Test
    fun `toonId에 해당하는 조회 이력이 존재하면 리스트로 반환할 수 있다`() {
        val id = 1L
        val histories =
            listOf(
                ToonViewHistoryEntity(toonId = id, userId = 1L, viewedAt = LocalDateTime.now()),
                ToonViewHistoryEntity(toonId = id, userId = 2L, viewedAt = LocalDateTime.now()),
            )

        `when`(toonViewHistoryRepository.findAllById(id)).thenReturn(histories)

        val actual = sut.findToonViewHistoryListBy(id = id)

        assertEquals(2, actual?.size)
        verify(toonViewHistoryRepository, times(1)).findAllById(id = id)
    }

    @Test
    fun `조회 이력이 없으면 빈 리스트를 반환할 수 있다`() {
        val id = 999L
        `when`(toonViewHistoryRepository.findAllById(id = id)).thenReturn(emptyList())

        val result = sut.findToonViewHistoryListBy(id = id)

        assertEquals(0, result?.size)
        verify(toonViewHistoryRepository, times(1)).findAllById(id = id)
    }
}
