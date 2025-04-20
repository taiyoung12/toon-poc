package com.comics.lezhin.toon.poc.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class EditableBaseEntity : BaseEntity() {
    @Column(
        name = "updated_at",
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition = "TIMESTAMP default current_timestamp() on update current_timestamp()",
    )
    private var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private var deletedAt: LocalDateTime? = null

    fun delete(deletedAt: LocalDateTime) {
        this.deletedAt = deletedAt
    }
}
