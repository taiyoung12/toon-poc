package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.entity.ToonEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ToonUpdater {
    fun deleteAll(
        time: LocalDateTime,
        toonEntityList: List<ToonEntity>,
    ) {
        toonEntityList.map {
            it.delete(deletedAt = time)
        }
    }
}
