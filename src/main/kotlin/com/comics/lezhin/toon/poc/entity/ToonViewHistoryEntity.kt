package com.comics.lezhin.toon.poc.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "toon_view_history")
class ToonViewHistoryEntity(
    userId: Long,
    toonId: Long,
    viewedAt: LocalDateTime,
) : EditableBaseEntity() {
    @Column(name = "user_id", nullable = false)
    var userId: Long = userId
        protected set

    @Column(name = "toon_id", nullable = false)
    var toonId: Long = toonId
        protected set

    @Column(name = "viewed_at", nullable = false)
    var viewedAt: LocalDateTime = viewedAt
        protected set
}
