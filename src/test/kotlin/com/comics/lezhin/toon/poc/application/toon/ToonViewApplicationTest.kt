package com.comics.lezhin.toon.poc.application.toon

import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.createGeneralToonRankings
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.createToonRankings
import com.comics.lezhin.toon.poc.controller.response.ReadToonViewHistoryResponse
import com.comics.lezhin.toon.poc.controller.response.toResponse
import com.comics.lezhin.toon.poc.entity.BaseEntity
import com.comics.lezhin.toon.poc.entity.ToonViewHistoryEntity
import com.comics.lezhin.toon.poc.entity.UserEntity
import com.comics.lezhin.toon.poc.service.auth.UserReader
import com.comics.lezhin.toon.poc.service.toon.ToonReader
import com.comics.lezhin.toon.poc.service.toon.ToonViewHistoryReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ToonViewApplicationTest {
    @Mock
    private lateinit var toonViewHistoryReader: ToonViewHistoryReader

    @Mock
    private lateinit var userReader: UserReader

    @Mock
    private lateinit var toonReader: ToonReader

    private lateinit var sut: ToonViewApplication

    @BeforeEach
    fun setUp() {
        sut =
            ToonViewApplication(
                toonViewHistoryReader = toonViewHistoryReader,
                userReader = userReader,
                toonReader = toonReader,
            )
    }

    @Test
    fun `조회 기록과 유저가 모두 존재하면 응답 객체를 반환할 수 있다`() {
        val toonId = 1L
        val historyList =
            listOf(
                ToonViewHistoryEntity(toonId = toonId, userId = 1L, viewedAt = LocalDateTime.now()),
                ToonViewHistoryEntity(toonId = toonId, userId = 2L, viewedAt = LocalDateTime.now()),
            )

        val user1 = UserEntity(email = "lezhin1@test.com", password = "pwd1", name = "한우진", age = 20)
        val user2 = UserEntity(email = "lezhin2@test.com", password = "pwd2", name = "도가영", age = 22)

        val idField = BaseEntity::class.java.getDeclaredField("id")
        idField.isAccessible = true
        idField.set(user1, 1L)
        idField.set(user2, 2L)

        val userList = listOf(user1, user2)

        `when`(toonViewHistoryReader.findToonViewHistoryListBy(toonId)).thenReturn(historyList)
        `when`(userReader.findAllUserBy(listOf(1L, 2L))).thenReturn(userList)

        val actual = sut.readToonViewHistory(toonId)

        assertNotNull(actual)
        assertTrue(actual is ReadToonViewHistoryResponse)
        verify(toonViewHistoryReader, times(1)).findToonViewHistoryListBy(toonId)
        verify(userReader, times(1)).findAllUserBy(listOf(1L, 2L))
    }

    @Test
    fun `조회기록에 있는 유저가 userEntityMap에 없으면 결과에 포함되지 않는다`() {
        val historyList =
            listOf(
                ToonViewHistoryEntity(toonId = 1L, userId = 1L, viewedAt = LocalDateTime.now()),
                ToonViewHistoryEntity(toonId = 1L, userId = 2L, viewedAt = LocalDateTime.now()),
                ToonViewHistoryEntity(toonId = 1L, userId = 3L, viewedAt = LocalDateTime.now()),
            )

        val user1 = UserEntity(email = "lezhin1@test.com", password = "pwd1", name = "한우진", age = 20)
        val user2 = UserEntity(email = "lezhin2@test.com", password = "pwd2", name = "도가영", age = 22)

        val idField = BaseEntity::class.java.getDeclaredField("id")
        idField.isAccessible = true
        idField.set(user1, 1L)
        idField.set(user2, 2L)

        val userMap = listOf(user1, user2).associateBy { it.getId() }

        val actual = historyList.toResponse(userMap)

        assertEquals(2, actual.viewHistories.size)
        assertTrue(actual.viewHistories.none { it.email == "lezhin3@test.com" })
    }

    @Test
    fun `조회 기록이 null이면 null을 반환한다`() {
        val toonId = 999L
        `when`(toonViewHistoryReader.findToonViewHistoryListBy(toonId)).thenReturn(null)

        val actual = sut.readToonViewHistory(toonId)

        assertNull(actual)
        verify(toonViewHistoryReader, times(1)).findToonViewHistoryListBy(toonId)
        verify(userReader, never()).findAllUserBy(anyList())
    }

    @Test
    fun `성인 유저는 성인 웹툰 랭킹을 조회할 수 있다`() {
        val userId = 1L
        val adultUser = UserEntity(email = "lezhin1@test.com", password = "lezhin123!", name = "한우진", age = 20)
        val toonRankings = createGeneralToonRankings()

        `when`(userReader.getBy(userId)).thenReturn(adultUser)
        `when`(toonReader.findPopularAdultToonTop10()).thenReturn(toonRankings)

        val actual = sut.readTop10(userId)

        assertEquals(1, actual.rankings[0].rank)

        verify(userReader, times(1)).getBy(userId)
        verify(toonReader, times(1)).findPopularAdultToonTop10()
        verify(toonReader, never()).findPopularGeneralToonTop10()
    }

    @Test
    fun `미성년자 유저는 일반 웹툰 랭킹을 조회할 수 있다`() {
        val userId = 1L
        val minorUser = UserEntity(email = "lezhin1@test.com", password = "lezhin123!", name = "도가영", age = 19)
        val toonRankings = createToonRankings()

        `when`(userReader.getBy(userId)).thenReturn(minorUser)
        `when`(toonReader.findPopularGeneralToonTop10()).thenReturn(toonRankings)

        val actual = sut.readTop10(userId)

        assertEquals(1, actual.rankings[0].rank)

        verify(userReader, times(1)).getBy(userId)
        verify(toonReader, never()).findPopularAdultToonTop10()
        verify(toonReader, times(1)).findPopularGeneralToonTop10()
    }
}
