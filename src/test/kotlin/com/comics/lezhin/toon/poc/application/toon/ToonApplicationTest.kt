package com.comics.lezhin.toon.poc.application.toon

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.eventPrice
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.minorUserEntity
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.toonEntity
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.toonId
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.toonPrice
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.toonPricePolicyEntity
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.toonViewHistoryEntityList
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.userCoinEntity
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.userEntity
import com.comics.lezhin.toon.poc.application.toon.fixture.ToonFixture.userId
import com.comics.lezhin.toon.poc.common.code.CoinCode
import com.comics.lezhin.toon.poc.common.code.ToonCode
import com.comics.lezhin.toon.poc.common.enums.toon.SourceType
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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ToonApplicationTest {
    @Mock
    private lateinit var toonPricePolicyReader: ToonPricePolicyReader

    @Mock
    private lateinit var toonReader: ToonReader

    @Mock
    private lateinit var userCoinReader: UserCoinReader

    @Mock
    private lateinit var userCoinUpdater: UserCoinUpdater

    @Mock
    private lateinit var coinTransactionSaver: CoinTransactionSaver

    @Mock
    private lateinit var toonPurchaseSaver: ToonPurchaseSaver

    @Mock
    private lateinit var toonPurchaseUpdater: ToonPurchaseUpdater

    @Mock
    private lateinit var toonViewHistoryReader: ToonViewHistoryReader

    @Mock
    private lateinit var toonUpdater: ToonUpdater

    @Mock
    private lateinit var userReader: UserReader

    private lateinit var sut: ToonApplication

    @BeforeEach
    fun setUp() {
        sut =
            ToonApplication(
                toonPricePolicyReader = toonPricePolicyReader,
                toonReader = toonReader,
                userCoinReader = userCoinReader,
                userCoinUpdater = userCoinUpdater,
                coinTransactionSaver = coinTransactionSaver,
                toonPurchaseSaver = toonPurchaseSaver,
                toonPurchaseUpdater = toonPurchaseUpdater,
                userReader = userReader,
                toonUpdater = toonUpdater,
                toonViewHistoryReader = toonViewHistoryReader,
            )
    }

    @Test
    fun `웹툰을 정상적으로 구매할 수 있다`() {
        `when`(userReader.getBy(userId)).thenReturn(userEntity)
        `when`(toonReader.getToonBy(toonId)).thenReturn(toonEntity)
        `when`(toonPricePolicyReader.findToonPricePolicyBy(toonId)).thenReturn(null)
        `when`(userCoinReader.getUserCoinBy(userId)).thenReturn(userCoinEntity)
        `when`(userCoinUpdater.decreaseCoin(userCoinEntity, toonPrice)).thenReturn(userCoinEntity)
        doNothing().`when`(toonPurchaseUpdater).updatePurchaseCount(toonId)

        sut.purchase(userId, toonId)

        verify(userReader, times(1)).getBy(userId)
        verify(toonReader, times(1)).getToonBy(toonId)
        verify(toonPricePolicyReader, times(1)).findToonPricePolicyBy(toonId)
        verify(userCoinReader, times(1)).getUserCoinBy(userId)
        verify(userCoinUpdater, times(1)).decreaseCoin(userCoinEntity, toonPrice)
        verify(coinTransactionSaver, times(1)).saveBalanceUseTransaction(
            userId,
            SourceType.PURCHASE,
            userCoinEntity,
            toonPrice,
        )
        verify(toonPurchaseSaver, times(1)).save(userId, toonId, toonPrice)
        verify(toonPurchaseUpdater, times(1)).updatePurchaseCount(toonId)
    }

    @Test
    fun `이벤트 가격정책이 있으면 이벤트 가격으로 구매할 수 있다`() {
        `when`(userReader.getBy(userId)).thenReturn(userEntity)
        `when`(toonReader.getToonBy(toonId)).thenReturn(toonEntity)
        `when`(toonPricePolicyReader.findToonPricePolicyBy(toonId)).thenReturn(toonPricePolicyEntity)
        `when`(userCoinReader.getUserCoinBy(userId)).thenReturn(userCoinEntity)
        `when`(userCoinUpdater.decreaseCoin(userCoinEntity, eventPrice)).thenReturn(userCoinEntity)
        doNothing().`when`(toonPurchaseUpdater).updatePurchaseCount(toonId)

        sut.purchase(userId, toonId)

        verify(toonPricePolicyReader, times(1)).findToonPricePolicyBy(toonId)
        verify(userCoinUpdater, times(1)).decreaseCoin(userCoinEntity, eventPrice)
        verify(coinTransactionSaver, times(1)).saveBalanceUseTransaction(
            userId,
            SourceType.EVENT,
            userCoinEntity,
            eventPrice,
        )
        verify(toonPurchaseSaver, times(1)).save(userId, toonId, eventPrice)
        verify(toonPurchaseUpdater, times(1)).updatePurchaseCount(toonId)
    }

    @Test
    fun `코인이 부족하면 구매할 수 없다`() {
        `when`(userReader.getBy(userId)).thenReturn(userEntity)
        `when`(toonReader.getToonBy(toonId)).thenReturn(toonEntity)
        `when`(toonPricePolicyReader.findToonPricePolicyBy(toonId)).thenReturn(null)
        `when`(userCoinReader.getUserCoinBy(userId)).thenReturn(userCoinEntity)
        `when`(userCoinUpdater.decreaseCoin(userCoinEntity, toonPrice))
            .thenThrow(BaseException(CoinCode.INSUFFICIENT_BALANCE))

        val exception =
            assertThrows<BaseException> {
                sut.purchase(userId, toonId)
            }

        assertEquals(CoinCode.INSUFFICIENT_BALANCE, exception.code)
        verify(toonPurchaseSaver, never()).save(anyLong(), anyLong(), anyInt())
    }

    @Test
    fun `성인 콘텐츠를 미성년자가 구매하려고 하면 예외가 발생한다`() {
        `when`(userReader.getBy(userId)).thenReturn(minorUserEntity)
        `when`(toonReader.getToonBy(toonId)).thenReturn(toonEntity)

        val exception =
            assertThrows<BaseException> {
                sut.purchase(userId, toonId)
            }

        assertEquals(ToonCode.FILTER_MINOR, exception.code)
        verify(toonPricePolicyReader, never()).findToonPricePolicyBy(anyLong())
        verify(userCoinReader, never()).getUserCoinBy(anyLong())
    }

    @Test
    fun `웹툰과 관련된 모든 정보를 삭제할 수 있다`() {
        val toonViewHistoryList = toonViewHistoryEntityList
        val deletedAt = LocalDateTime.now()

        `when`(toonReader.getToonBy(toonId)).thenReturn(toonEntity)
        `when`(toonViewHistoryReader.findToonViewHistoryListBy(toonId)).thenReturn(toonViewHistoryList)
        doNothing().`when`(toonUpdater).deleteAll(deletedAt, toonEntity, toonViewHistoryList)

        sut.deleteToonAllInfo(deletedAt, toonId)

        verify(toonReader, times(1)).getToonBy(toonId)
        verify(toonViewHistoryReader, times(1)).findToonViewHistoryListBy(toonId)
        verify(toonUpdater, times(1)).deleteAll(deletedAt, toonEntity, toonViewHistoryList)
    }
}
