package com.comics.lezhin.toon.poc.controller.response

import com.comics.lezhin.toon.poc.entity.ToonViewHistoryEntity
import com.comics.lezhin.toon.poc.entity.UserEntity
import java.time.LocalDateTime

data class ReadToonViewHistoryResponse(
    val viewHistories: List<ToonViewHistoryDto>,
)

data class ToonViewHistoryDto(
    val name: String,
    val email: String,
    val viewedAt: List<LocalDateTime>,
)

fun List<ToonViewHistoryEntity>.toResponse(userEntityMap: Map<Long, UserEntity>?): ReadToonViewHistoryResponse {
    val grouped = this.groupBy { it.userId }

    val dtoList =
        grouped.mapNotNull { (userId, histories) ->
            val user = userEntityMap?.get(userId) ?: return@mapNotNull null

            ToonViewHistoryDto(
                name = user.name,
                email = user.email,
                viewedAt = histories.map { it.viewedAt },
            )
        }

    return ReadToonViewHistoryResponse(viewHistories = dtoList)
}
