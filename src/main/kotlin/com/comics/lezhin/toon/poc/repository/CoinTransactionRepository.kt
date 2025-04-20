package com.comics.lezhin.toon.poc.repository

import com.comics.lezhin.toon.poc.entity.CoinTransactionEntity
import org.springframework.data.repository.Repository

interface CoinTransactionRepository : Repository<CoinTransactionEntity, Long> {
    fun save(coinTransactionEntity: CoinTransactionEntity)
}
