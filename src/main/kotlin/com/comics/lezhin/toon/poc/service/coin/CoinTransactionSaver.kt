package com.comics.lezhin.toon.poc.service.coin

import com.comics.lezhin.toon.poc.common.enums.toon.CoinTransactionType
import com.comics.lezhin.toon.poc.common.enums.toon.SourceType
import com.comics.lezhin.toon.poc.entity.CoinTransactionEntity
import com.comics.lezhin.toon.poc.entity.UserCoinEntity
import com.comics.lezhin.toon.poc.repository.CoinTransactionRepository
import org.springframework.stereotype.Service

@Service
class CoinTransactionSaver(
    private val coinTransactionRepository: CoinTransactionRepository,
) {
    fun saveBalanceUseTransaction(
        userId: Long,
        sourceType: SourceType,
        userCoinEntity: UserCoinEntity,
        deductBalance: Int,
    ) {
        val coinTransactionEntity =
            CoinTransactionEntity(
                userId = userId,
                coinTransactionType = CoinTransactionType.USE,
                sourceType = sourceType,
                amount = userCoinEntity.balance,
                usedAmount = deductBalance,
            )

        coinTransactionRepository.save(coinTransactionEntity)
    }
}
