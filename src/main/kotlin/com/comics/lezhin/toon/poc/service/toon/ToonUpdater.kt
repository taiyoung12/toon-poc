package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.entity.ToonEntity
import com.comics.lezhin.toon.poc.entity.ToonViewHistoryEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ToonUpdater {
    fun deleteAll(
        deletedAt: LocalDateTime,
        toonEntity: ToonEntity,
        toonViewHistoryEntityList: List<ToonViewHistoryEntity>?,
    ) {
        toonEntity.delete(deletedAt = deletedAt)
        toonViewHistoryEntityList?.map {
            it.delete(deletedAt = deletedAt)
        }
    }
}
