package com.comics.lezhin.toon.poc.repository

import com.comics.lezhin.toon.poc.entity.UserCoinEntity
import org.springframework.data.repository.Repository

interface UserCoinRepository : Repository<UserCoinEntity, Long> {
    fun findByUserId(userId: Long): UserCoinEntity
}
