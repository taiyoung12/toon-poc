package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.entity.ToonViewHistoryEntity
import com.comics.lezhin.toon.poc.repository.ToonViewHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ToonViewHistoryReader(
    private val toonViewHistoryRepository: ToonViewHistoryRepository,
) {
    @Transactional(readOnly = true)
    fun findToonViewHistoryListBy(id: Long): List<ToonViewHistoryEntity>? {
        val toonViewHistoryEntityList = toonViewHistoryRepository.findAllById(id = id)
        return toonViewHistoryEntityList
    }
}
