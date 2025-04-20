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
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UserCoinUpdaterTest {
    @Mock
    private lateinit var userCoinRepository: UserCoinRepository

    private lateinit var sut: UserCoinUpdater

    @BeforeEach
    fun setUp() {
        sut =
            UserCoinUpdater(
                userCoinRepository = userCoinRepository,
            )
    }

    @Test
    fun `코인을 정상적으로 차감할 수 있다`() {
        val userId = 1L
        val initialBalance = 50
        val deductBalance = 3
        val userCoinEntity = UserCoinEntity(userId = userId, balance = initialBalance)

        val actual = sut.decreaseCoin(userCoinEntity = userCoinEntity, deductBalance = deductBalance)

        assertEquals(initialBalance - deductBalance, actual.balance)
        verify(userCoinRepository, times(1)).save(userCoinEntity)
    }

    @Test
    fun `잔액이 부족하면 예외가 발생할 수 있다`() {
        val userId = 1L
        val initialBalance = 2
        val deductAmount = 3
        val userCoinEntity = UserCoinEntity(userId = userId, balance = initialBalance)

        val exception =
            assertThrows<BaseException> {
                sut.decreaseCoin(userCoinEntity = userCoinEntity, deductBalance = deductAmount)
            }

        assertEquals(CoinCode.INSUFFICIENT_BALANCE, exception.code)
        verify(userCoinRepository, times(0)).save(userCoinEntity)
    }
}
