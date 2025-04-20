package com.comics.lezhin.toon.poc.entity

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.code.CoinCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user_coin")
class UserCoinEntity(
    userId: Long,
    balance: Int,
) : EditableBaseEntity() {
    @Column(nullable = false)
    var userId: Long = userId
        protected set

    @Column(nullable = false)
    var balance: Int = balance
        protected set

    fun purchase(deductBalance: Int) {
        if (deductBalance > this.balance) {
            throw BaseException(CoinCode.INSUFFICIENT_BALANCE)
        }

        this.balance -= deductBalance
    }
}
