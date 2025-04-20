package com.comics.lezhin.toon.poc.repository

import com.comics.lezhin.toon.poc.entity.UserEntity
import org.springframework.data.repository.Repository

interface UserRepository : Repository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?

    fun findAllByIdIn(idList: List<Long>): List<UserEntity>

    fun findById(id: Long): UserEntity?
}
