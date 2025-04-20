package com.comics.lezhin.toon.poc.repository

import com.comics.lezhin.toon.poc.entity.UserCoinEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface UserCoinRepository : Repository<UserCoinEntity, Long> {
    fun findByUserId(userId: Long): UserCoinEntity?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT uc FROM UserCoinEntity uc WHERE uc.userId = :userId")
    fun findByUserIdForUpdate(userId: Long): UserCoinEntity?
}
