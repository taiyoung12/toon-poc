package com.comics.lezhin.toon.poc.application.toon

import com.comics.lezhin.toon.poc.app.annotation.ApplicationLayer
import com.comics.lezhin.toon.poc.common.enums.toon.SourceType
import com.comics.lezhin.toon.poc.entity.ToonEntity
import com.comics.lezhin.toon.poc.entity.ToonPricePolicyEntity
import com.comics.lezhin.toon.poc.entity.UserCoinEntity
import com.comics.lezhin.toon.poc.service.auth.UserReader
import com.comics.lezhin.toon.poc.service.coin.CoinTransactionSaver
import com.comics.lezhin.toon.poc.service.coin.UserCoinReader
import com.comics.lezhin.toon.poc.service.coin.UserCoinUpdater
import com.comics.lezhin.toon.poc.service.toon.ToonPricePolicyReader
import com.comics.lezhin.toon.poc.service.toon.ToonPurchaseSaver
import com.comics.lezhin.toon.poc.service.toon.ToonPurchaseUpdater
import com.comics.lezhin.toon.poc.service.toon.ToonReader
import com.comics.lezhin.toon.poc.service.toon.ToonUpdater
import com.comics.lezhin.toon.poc.service.toon.ToonViewHistoryReader
import com.comics.lezhin.toon.poc.service.toon.ToonViewHistoryUpdater
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@ApplicationLayer
class ToonApplication(
    private val toonPricePolicyReader: ToonPricePolicyReader,
    private val toonReader: ToonReader,
    private val userCoinReader: UserCoinReader,
    private val userCoinUpdater: UserCoinUpdater,
    private val coinTransactionSaver: CoinTransactionSaver,
    private val toonPurchaseSaver: ToonPurchaseSaver,
    private val toonPurchaseUpdater: ToonPurchaseUpdater,
    private val userReader: UserReader,
    private val toonViewHistoryReader: ToonViewHistoryReader,
    private val toonUpdater: ToonUpdater,
    private val toonViewHistoryUpdater: ToonViewHistoryUpdater,
) {
    @Transactional
    fun purchase(
        userId: Long,
        toonId: Long,
    ): Int {
        val userEntity = userReader.getBy(id = userId)
        val toonEntity = toonReader.getToonBy(id = toonId)
        toonEntity.filter(userEntity = userEntity)

        val toonPricePolicyEntity = toonPricePolicyReader.findToonPricePolicyBy(toonId = toonId)
        val deductBalance = caculateBalance(toonPricePolicyEntity, toonEntity)
        val sourceType = determineSourceType(toonPricePolicyEntity)

        val userCoinEntity =
            executePurchaseTransaction(
                userId = userId,
                toonId = toonId,
                deductBalance = deductBalance,
                sourceType = sourceType,
            )

        return userCoinEntity.balance
    }

    @Transactional
    fun deleteToonAllInfo(toonId: Long) {
        val toonEntity = toonReader.getToonBy(id = toonId)
        val toonViewHistoryEntityList = toonViewHistoryReader.findToonViewHistoryListBy(id = toonId)

        val deletedAt = LocalDateTime.now()

        toonUpdater.deleteAll(
            deletedAt = deletedAt,
            toonEntity = toonEntity,
            toonViewHistoryEntityList = toonViewHistoryEntityList,
        )
    }

    fun executePurchaseTransaction(
        userId: Long,
        toonId: Long,
        deductBalance: Int,
        sourceType: SourceType,
    ): UserCoinEntity {
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

        toonPurchaseUpdater.updatePurchaseCount(toonId = toonId)

        return userCoinEntity
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
