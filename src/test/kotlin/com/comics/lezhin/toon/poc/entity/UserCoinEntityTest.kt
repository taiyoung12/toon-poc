package com.comics.lezhin.toon.poc.entity

import com.comics.lezhin.toon.poc.app.exception.BaseException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserCoinEntityTest {
    @Test
    fun `UserCoinEntity를 생성할 수 있다`() {
        val userId = 1L
        val balance = 1000

        val actual =
            UserCoinEntity(
                userId = userId,
                balance = balance,
            )

        assertEquals(userId, actual.userId)
        assertEquals(balance, actual.balance)
    }

    @Test
    fun `coin을 차감할 수 있다`() {
        val userId = 1L
        val balance = 50
        val deductBalance = 3

        val actual =
            UserCoinEntity(
                userId = userId,
                balance = balance,
            )

        actual.purchase(deductBalance)
        assertEquals(actual.balance, balance - deductBalance)
    }

    @Test
    fun `경계값을 가진 coin을 차감할 수 있다`() {
        val userId = 1L
        val balance = 3
        val deductBalance = 3

        val actual =
            UserCoinEntity(
                userId = userId,
                balance = balance,
            )

        actual.purchase(deductBalance)
        assertEquals(actual.balance, balance - deductBalance)
    }

    @Test
    fun `coin이 부족하다면 예외가 발생할 수 있다`() {
        val userId = 1L
        val balance = 2
        val deductBalance = 3

        val actual =
            UserCoinEntity(
                userId = userId,
                balance = balance,
            )

        assertThrows<BaseException> {
            actual.purchase(deductBalance)
        }

        assertEquals(actual.balance, balance)
    }
}
