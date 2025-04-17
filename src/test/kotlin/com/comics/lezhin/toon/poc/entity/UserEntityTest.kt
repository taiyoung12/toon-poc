package com.comics.lezhin.toon.poc.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserEntityTest {
    @Test
    fun `UserEntity를 생성할 수 있다`() {
        val name = "김레진"
        val age = 19

        val actual =
            UserEntity(
                name = name,
                age = age,
            )

        assertEquals(name, actual.name)
        assertEquals(age, actual.age)
    }
}
