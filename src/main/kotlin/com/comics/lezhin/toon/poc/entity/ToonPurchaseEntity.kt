package com.comics.lezhin.toon.poc.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "toon_purchase")
class ToonPurchaseEntity(
    userId: Long,
    toonId: Long,
    toonPricePolicyId: Long,
    purchasedAt: LocalDateTime,
    price: Int,
) : BaseEntity() {
    @Column(name = "user_id", nullable = false)
    var userId: Long = userId
        protected set

    @Column(name = "toon_id", nullable = false)
    var toonId: Long = toonId
        protected set

    @Column(name = "toon_price_policy_id", nullable = false)
    var toonPricePolicyId: Long = toonPricePolicyId
        protected set

    @Column(name = "purchased_at", nullable = false)
    var purchasedAt: LocalDateTime = purchasedAt
        protected set

    @Column(name = "price", nullable = false)
    var price: Int = price
        protected set
}
