package com.comics.lezhin.toon.poc.application.toon

import com.comics.lezhin.toon.poc.app.annotation.ApplicationLayer
import com.comics.lezhin.toon.poc.common.enums.toon.SourceType
import com.comics.lezhin.toon.poc.entity.ToonEntity
import com.comics.lezhin.toon.poc.entity.ToonPricePolicyEntity
import com.comics.lezhin.toon.poc.service.auth.UserReader
import com.comics.lezhin.toon.poc.service.coin.CoinTransactionSaver
import com.comics.lezhin.toon.poc.service.coin.UserCoinReader
import com.comics.lezhin.toon.poc.service.coin.UserCoinUpdater
import com.comics.lezhin.toon.poc.service.toon.ToonPricePolicyReader
import com.comics.lezhin.toon.poc.service.toon.ToonPurchaseSaver
import com.comics.lezhin.toon.poc.service.toon.ToonReader
import org.springframework.transaction.annotation.Transactional

@ApplicationLayer
class ToonApplication(
    private val toonPricePolicyReader: ToonPricePolicyReader,
    private val toonReader: ToonReader,
    private val userCoinReader: UserCoinReader,
    private val userCoinUpdater: UserCoinUpdater,
    private val coinTransactionSaver: CoinTransactionSaver,
    private val toonPurchaseSaver: ToonPurchaseSaver,
    private val userReader: UserReader,
) {
    @Transactional
    fun purchase(
        userId: Long,
        toonId: Long,
    ) {
        val userEntity = userReader.getBy(id = userId)
        val toonEntity = toonReader.getToonBy(id = toonId)
        toonEntity.filter(userEntity = userEntity)

        val toonPricePolicyEntity = toonPricePolicyReader.findToonPricePolicyBy(toonId = toonId)
        val deductBalance = caculateBalance(toonPricePolicyEntity, toonEntity)
        val sourceType = determineSourceType(toonPricePolicyEntity)

        executePurchaseTransaction(
            userId = userId,
            toonId = toonId,
            deductBalance = deductBalance,
            sourceType = sourceType,
        )
    }

    fun executePurchaseTransaction(
        userId: Long,
        toonId: Long,
        deductBalance: Int,
        sourceType: SourceType,
    ) {
        val userCoinEntity = userCoinReader.getUserCoinBy(userId = userId)

        userCoinUpdater.decreaseCoin(userCoinEntity = userCoinEntity, deductBalance = deductBalance)

        coinTransactionSaver.saveBalanceUseTransaction(
            userId = userId,
            sourceType = sourceType,
            userCoinEntity = userCoinEntity,
            deductBalance = deductBalance,
        )

        toonPurchaseSaver.save(
            userId = userId,
            toonId = toonId,
            deductBalance = deductBalance,
        )
    }

    private fun determineSourceType(toonPricePolicyEntity: ToonPricePolicyEntity?): SourceType {
        val sourceType = toonPricePolicyEntity?.let { SourceType.EVENT } ?: SourceType.PURCHASE
        return sourceType
    }

    private fun caculateBalance(
        toonPricePolicyEntity: ToonPricePolicyEntity?,
        toonEntity: ToonEntity,
    ): Int {
        val deductBalance = toonPricePolicyEntity?.price ?: toonEntity.price
        return deductBalance
    }
}
