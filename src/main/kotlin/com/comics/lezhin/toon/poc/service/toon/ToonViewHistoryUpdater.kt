package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.entity.ToonViewHistoryEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ToonViewHistoryUpdater {
    fun deleteAll(
        time: LocalDateTime,
        toonViewHistoryEntityList: List<ToonViewHistoryEntity>,
    ) {
        toonViewHistoryEntityList.map {
            it.delete(deletedAt = time)
        }
    }
}
