package com.comics.lezhin.toon.poc.entity

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
}
