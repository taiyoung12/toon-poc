package com.comics.lezhin.toon.poc.repository

import com.comics.lezhin.toon.poc.entity.ToonPricePolicyEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface ToonPricePolicyRepository : Repository<ToonPricePolicyEntity, Long> {
    @Query(
        """
        SELECT tpp FROM ToonPricePolicyEntity tpp 
        WHERE tpp.toonId = :toonId 
          AND :now BETWEEN tpp.startDate AND tpp.endDate
        """,
    )
    fun findValidPolicy(
        @Param("toonId") toonId: Long,
        @Param("now") now: LocalDate,
    ): ToonPricePolicyEntity?
}
