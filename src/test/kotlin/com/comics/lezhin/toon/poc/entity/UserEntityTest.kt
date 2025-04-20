package com.comics.lezhin.toon.poc.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserEntityTest {
    @Test
    fun `UserEntity를 생성할 수 있다`() {
        val email = "lezhin@naver.com"
        val password = "lezhin123"
        val name = "김레진"
        val age = 19

        val actual =
            UserEntity(
                email = email,
                password = password,
                name = name,
                age = age,
            )

        assertEquals(email, actual.email)
        assertEquals(password, actual.password)
        assertEquals(name, actual.name)
        assertEquals(age, actual.age)
    }

    @Test
    fun `성인이 아님을 필터할 수 있다`() {
        val email = "lezhin@naver.com"
        val password = "lezhin123"
        val name = "김레진"
        val age = 19

        val actual =
            UserEntity(
                email = email,
                password = password,
                name = name,
                age = age,
            )

        assertEquals(actual.filter(), false)
    }

    @Test
    fun `성인임을 필터할 수 있다`() {
        val email = "lezhin@naver.com"
        val password = "lezhin123"
        val name = "김레진"
        val age = 20

        val actual =
            UserEntity(
                email = email,
                password = password,
                name = name,
                age = age,
            )

        assertEquals(actual.filter(), true)
    }
}
