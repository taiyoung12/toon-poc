package com.comics.lezhin.toon.poc.service

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.code.AuthCode
import com.comics.lezhin.toon.poc.entity.UserEntity
import com.comics.lezhin.toon.poc.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UserReaderTest {
    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var sut: UserReader

    @BeforeEach
    fun setUp() {
        sut =
            UserReader(
                userRepository = userRepository,
            )
    }

    @Test
    fun `email에  맞는 유저가 있다면 조회할 수 있다`() {
        val email = "woojin@lezhin.com"
        val password = "password"
        val name = "한우진"
        val age = 19

        val user =
            UserEntity(
                email = email,
                password = password,
                name = name,
                age = age,
            )

        `when`(userRepository.findByEmail(email = email)).thenReturn(user)

        sut.getUserBy(email = email)

        verify(userRepository, times(1)).findByEmail(email)
    }

    @Test
    fun `이메일로 유저 조회 실패시 예외가 발생할 수 있다`() {
        val email = "notfound@example.com"
        `when`(userRepository.findByEmail(email)).thenReturn(null)

        val exception =
            assertThrows<BaseException> {
                sut.getUserBy(email)
            }

        assertEquals(AuthCode.NOT_FOUND_USER_BY_EMAIL, exception.code)
    }
}
