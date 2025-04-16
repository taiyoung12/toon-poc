package com.comics.lezhin.toon.poc.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
class UserEntity(
    name: String,
    isAdult: Boolean,
) : EditableBaseEntity() {
    @Column(nullable = false)
    var name: String = name
        protected set

    @Column(name = "is_adult", nullable = false)
    var isAdult: Boolean = isAdult
        protected set
}
