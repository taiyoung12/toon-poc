package com.comics.lezhin.toon.poc.repository

import com.comics.lezhin.toon.poc.entity.ToonEntity
import org.springframework.data.repository.Repository

interface ToonRepository : Repository<ToonEntity, Long> {
    fun findById(id: Long): ToonEntity?

    fun findAll(): List<ToonEntity>
}
