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
    email: String,
    password: String,
    name: String,
    age: Int,
) : EditableBaseEntity() {
    @Column(nullable = false)
    var email: String = email
        protected set

    @Column(nullable = false)
    var password: String = password
        protected set

    @Column(nullable = false)
    var name: String = name
        protected set

    @Column(name = "age", nullable = false)
    var age: Int = age
        protected set
}
