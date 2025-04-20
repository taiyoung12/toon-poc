package com.comics.lezhin.toon.poc.entity

import com.comics.lezhin.toon.poc.common.enums.toon.CoinTransactionType
import com.comics.lezhin.toon.poc.common.enums.toon.SourceType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "coin_transaction")
class CoinTransactionEntity(
    userId: Long,
    coinTransactionType: CoinTransactionType,
    sourceType: SourceType,
    amount: Int,
    usedAmount: Int,
) : BaseEntity() {
    @Column(nullable = false)
    var userId: Long = userId
        protected set

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var sourceType: SourceType = sourceType
        protected set

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var coinTransactionType: CoinTransactionType = coinTransactionType
        protected set

    @Column(nullable = false)
    var amount: Int = amount
        protected set

    @Column(nullable = false)
    var usedAmount: Int = usedAmount
        protected set
}
