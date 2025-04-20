package com.comics.lezhin.toon.poc.service.coin

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.code.CoinCode
import com.comics.lezhin.toon.poc.entity.UserCoinEntity
import com.comics.lezhin.toon.poc.repository.UserCoinRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCoinReader(
    private val userCoinRepository: UserCoinRepository,
) {
    @Transactional(readOnly = true)
    fun getUserCoinBy(userId: Long): UserCoinEntity {
        val userCoinEntity = userCoinRepository.findByUserIdForUpdate(userId = userId) ?: throw BaseException(CoinCode.NO_CHARGE_HISTORY)
        return userCoinEntity
    }
}
