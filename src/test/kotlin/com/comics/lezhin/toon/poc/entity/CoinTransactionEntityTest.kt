package com.comics.lezhin.toon.poc.entity

import com.comics.lezhin.toon.poc.common.enums.toon.CoinTransactionType
import com.comics.lezhin.toon.poc.common.enums.toon.SourceType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CoinTransactionEntityTest {
    @Test
    fun `CoinTransactionEntity를 생성할 수 있다`() {
        val userId = 1L
        val coinTransactionType = CoinTransactionType.CHARGE
        val sourceType = SourceType.EVENT
        val amount = 1000
        val usedAmount = 0

        val actual = CoinTransactionEntity(
            userId = userId,
            coinTransactionType = coinTransactionType,
            sourceType = sourceType,
            amount = amount,
            usedAmount = usedAmount,
        )

        assertEquals(userId, actual.userId)
        assertEquals(coinTransactionType, actual.coinTransactionType)
        assertEquals(sourceType, actual.sourceType)
        assertEquals(amount, actual.amount)
        assertEquals(usedAmount, actual.usedAmount)
    }
}