package com.comics.lezhin.toon.poc.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDate

@Entity
@Table(name = "webtoon_price_policy")
@SQLDelete(sql = "UPDATE webtoon_price_policy SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
class ToonPricePolicyEntity(
    toonId: Long,
    startDate: LocalDate,
    endDate: LocalDate,
    price: Int,
) : EditableBaseEntity() {
    @Column(name = "webtoon_id", nullable = false)
    var toonId: Long = toonId
        protected set

    @Column(name = "start_date", nullable = false)
    var startDate: LocalDate = startDate
        protected set

    @Column(name = "end_date", nullable = false)
    var endDate: LocalDate = endDate
        protected set

    @Column(nullable = false)
    var price: Int = price
        protected set
}
