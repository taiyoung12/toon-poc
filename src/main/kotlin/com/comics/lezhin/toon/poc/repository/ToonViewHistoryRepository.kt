package com.comics.lezhin.toon.poc.repository

import com.comics.lezhin.toon.poc.entity.ToonViewHistoryEntity
import org.springframework.data.repository.Repository

interface ToonViewHistoryRepository : Repository<ToonViewHistoryEntity, Long> {
    fun findAllByToonId(id: Long): List<ToonViewHistoryEntity>
}
