package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.entity.ToonPricePolicyEntity
import com.comics.lezhin.toon.poc.repository.ToonPricePolicyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ToonPricePolicyReader(
    private val toonPricePolicyRepository: ToonPricePolicyRepository,
) {
    @Transactional(readOnly = true)
    fun findToonPricePolicyBy(toonId: Long): ToonPricePolicyEntity? =
        toonPricePolicyRepository.findValidPolicy(toonId = toonId, now = LocalDate.now())
}
