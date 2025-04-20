package com.comics.lezhin.toon.poc.application.auth

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.app.token.JwtGenerator
import com.comics.lezhin.toon.poc.common.code.AuthCode
import com.comics.lezhin.toon.poc.common.utils.PasswordMatcher
import com.comics.lezhin.toon.poc.entity.UserEntity
import com.comics.lezhin.toon.poc.service.auth.UserReader
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
class AuthApplicationTest {
    @Mock
    private lateinit var userReader: UserReader

    @Mock
    private lateinit var passwordMatcher: PasswordMatcher

    @Mock
    private lateinit var jwtGenerator: JwtGenerator

    private lateinit var sut: AuthApplication

    @BeforeEach
    fun setUp() {
        sut =
            AuthApplication(
                userReader = userReader,
                passwordMatcher = passwordMatcher,
                jwtGenerator = jwtGenerator,
            )
    }

    @Test
    fun `이메일과 비밀번호가 일치하면 토큰을 반환할 수 있다`() {
        val email = "woojin@lezhin.com"
        val rawPassword = "password"
        val encodedPassword = "encoded-password"
        val token = "jwt-token"

        val user =
            UserEntity(
                email = email,
                password = encodedPassword,
                name = "한우진",
                age = 20,
            )

        `when`(userReader.getUserBy(email)).thenReturn(user)
        `when`(passwordMatcher.matches(rawPassword, encodedPassword)).thenReturn(true)
        `when`(jwtGenerator.generateAccessToken(user.getId().toString())).thenReturn(token)

        val actual = sut.signin(email, rawPassword)

        assertEquals(token, actual)
        verify(userReader, times(1)).getUserBy(email)
        verify(passwordMatcher, times(1)).matches(rawPassword, encodedPassword)
        verify(jwtGenerator, times(1)).generateAccessToken(user.getId().toString())
    }

    @Test
    fun `비밀번호가 일치하지 않으면 예외가 발생할 수 있다`() {
        val email = "woojin@lezhin.com"
        val rawPassword = "wrong-password"
        val encodedPassword = "encoded-password"

        val user =
            UserEntity(
                email = email,
                password = encodedPassword,
                name = "한우진",
                age = 20,
            )

        `when`(userReader.getUserBy(email)).thenReturn(user)
        `when`(passwordMatcher.matches(rawPassword, encodedPassword)).thenReturn(false)

        val exception =
            assertThrows<BaseException> {
                sut.signin(email, rawPassword)
            }

        assertEquals(AuthCode.NOT_MATCHED_USER_PASSWORD, exception.code)
        verify(userReader, times(1)).getUserBy(email)
        verify(passwordMatcher, times(1)).matches(rawPassword, encodedPassword)
    }
}
