package com.comics.lezhin.toon.poc.service.toon

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.code.ToonCode
import com.comics.lezhin.toon.poc.entity.ToonEntity
import com.comics.lezhin.toon.poc.inmemory.ViewRepository
import com.comics.lezhin.toon.poc.repository.ToonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ToonReader(
    private val toonRepository: ToonRepository,
    private val viewRepository: ViewRepository,
) {
    @Transactional(readOnly = true)
    fun getToonBy(id: Long): ToonEntity {
        val toonEntity = toonRepository.findById(id = id) ?: throw BaseException(ToonCode.NOT_FOUND_TOON_BY_ID)
        return toonEntity
    }

    fun findPopularAdultToonTop10() = viewRepository.getAdultTop10()

    fun findPopularGeneralToonTop10() = viewRepository.getGeneralTop10()
}
