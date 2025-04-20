package com.comics.lezhin.toon.poc.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserCoinEntityTest {
    @Test
    fun `UserCoinEntity를 생성할 수 있다`() {
        val userId = 1L
        val balance = 1000

        val actual = UserCoinEntity(
            userId = userId,
            balance = balance,
        )

        assertEquals(userId, actual.userId)
        assertEquals(balance, actual.balance)
    }
}
