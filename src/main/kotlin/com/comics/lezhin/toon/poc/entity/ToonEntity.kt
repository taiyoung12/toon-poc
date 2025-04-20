package com.comics.lezhin.toon.poc.entity

import com.comics.lezhin.toon.poc.common.enums.toon.Genre
import com.comics.lezhin.toon.poc.common.enums.toon.PriceType
import com.comics.lezhin.toon.poc.common.enums.toon.ScheduleDay
import com.comics.lezhin.toon.poc.common.enums.toon.ToonState
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "toon")
@SQLDelete(sql = "UPDATE webtoons SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
class ToonEntity(
    title: String,
    isAdultOnly: Boolean,
    price: Int,
    priceType: PriceType,
    toonState: ToonState,
    genre: Genre,
    scheduleDay: ScheduleDay,
) : EditableBaseEntity() {
    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(name = "is_adult_only", nullable = false)
    var isAdultOnly: Boolean = isAdultOnly
        protected set

    @Column(name = "price", nullable = false)
    var price: Int = price
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "price_type", nullable = false)
    var priceType: PriceType = priceType
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    var toonState: ToonState = toonState
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    var genre: Genre = genre
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_day", nullable = false)
    var scheduleDay: ScheduleDay = scheduleDay
        protected set
}
