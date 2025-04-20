package com.comics.lezhin.toon.poc.service.coin

import com.comics.lezhin.toon.poc.entity.UserCoinEntity
import com.comics.lezhin.toon.poc.repository.UserCoinRepository
import org.springframework.stereotype.Service

@Service
class UserCoinUpdater(
    private val userCoinRepository: UserCoinRepository,
) {
    fun decreaseCoin(
        userCoinEntity: UserCoinEntity,
        deductBalance: Int,
    ): UserCoinEntity {
        userCoinEntity.purchase(deductBalance = deductBalance)
        userCoinRepository.save(userCoinEntity = userCoinEntity)
        return userCoinEntity
    }
}
