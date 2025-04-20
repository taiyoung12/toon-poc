package com.comics.lezhin.toon.poc.service.coin

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.code.CoinCode
import com.comics.lezhin.toon.poc.entity.UserCoinEntity
import com.comics.lezhin.toon.poc.repository.UserCoinRepository
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
class UserCoinReaderTest {
    @Mock
    private lateinit var userCoinRepository: UserCoinRepository

    private lateinit var sut: UserCoinReader

    @BeforeEach
    fun setUp() {
        sut =
            UserCoinReader(
                userCoinRepository = userCoinRepository,
            )
    }

    @Test
    fun `userId에 해당하는 userCoin이 있으면 조회할 수 있다`() {
        val userId = 1L
        val userCoinEntity =
            UserCoinEntity(
                userId = userId,
                balance = 30,
            )

        `when`(userCoinRepository.findByUserId(userId)).thenReturn(userCoinEntity)

        sut.getUserCoinBy(userId)

        verify(userCoinRepository, times(1)).findByUserId(userId)
    }

    @Test
    fun `userId에 해당하는 userCoin이 없으면 예외가 발생할 수 있다`() {
        val userId = 999L

        `when`(userCoinRepository.findByUserId(userId)).thenReturn(null)

        val actual =
            assertThrows<BaseException> {
                sut.getUserCoinBy(userId)
            }

        assertEquals(CoinCode.NO_CHARGE_HISTORY, actual.code)
        verify(userCoinRepository, times(1)).findByUserId(userId)
    }
}
