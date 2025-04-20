package com.comics.lezhin.toon.poc.repository

import com.comics.lezhin.toon.poc.entity.ToonPurchaseEntity
import org.springframework.data.repository.Repository

interface ToonPurchaseRepository : Repository<ToonPurchaseEntity, Long> {
    fun save(toonPurchaseEntity: ToonPurchaseEntity)
}
