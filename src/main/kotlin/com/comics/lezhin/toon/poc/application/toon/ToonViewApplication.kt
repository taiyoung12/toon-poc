package com.comics.lezhin.toon.poc.application.toon

import com.comics.lezhin.toon.poc.app.annotation.ApplicationLayer
import com.comics.lezhin.toon.poc.controller.response.ReadToonRankResponse
import com.comics.lezhin.toon.poc.controller.response.ReadToonViewHistoryResponse
import com.comics.lezhin.toon.poc.controller.response.toResponse
import com.comics.lezhin.toon.poc.service.auth.UserReader
import com.comics.lezhin.toon.poc.service.toon.ToonReader
import com.comics.lezhin.toon.poc.service.toon.ToonViewHistoryReader

@ApplicationLayer
class ToonViewApplication(
    private val toonViewHistoryReader: ToonViewHistoryReader,
    private val userReader: UserReader,
    private val toonReader: ToonReader,
) {
    companion object {
        private val VALID_USER_NOT_FOUND = null
    }

    fun readToonViewHistory(id: Long): ReadToonViewHistoryResponse? {
        val toonViewHistoryEntityList = toonViewHistoryReader.findToonViewHistoryListBy(id = id)
        val userIdList = toonViewHistoryEntityList?.map { it.userId } ?: return VALID_USER_NOT_FOUND

        val userEntityList = userReader.findAllUserBy(idList = userIdList)
        val userEntityMap = userEntityList?.associateBy { it.getId() }

        return toonViewHistoryEntityList.toResponse(userEntityMap = userEntityMap)
    }

    fun readTop10(userId: Long): ReadToonRankResponse {
        val userEntity = userReader.getBy(id = userId)
        return when (userEntity.filter()) {
            true -> toonReader.findPopularAdultToonTop10().toResponse()
            false -> toonReader.findPopularGeneralToonTop10().toResponse()
        }
    }
}
