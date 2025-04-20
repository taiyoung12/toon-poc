package com.comics.lezhin.toon.poc.service.auth

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

    @Test
    fun `ID 목록으로 유저들을 조회할 수 있다`() {
        val idList = listOf(1L, 2L)
        val userEntityList =
            listOf(
                UserEntity(name = "한우진", email = "lezhin1@test.com", password = "encoded", age = 30),
                UserEntity(name = "도가영", email = "lezhin2@test.com", password = "encoded", age = 28),
            )

        `when`(userRepository.findAllByIdIn(idList = idList)).thenReturn(userEntityList)

        val actual = sut.findAllUserBy(idList = idList)

        assertEquals(actual?.size, 2)
        verify(userRepository, times(1)).findAllByIdIn(idList = idList)
    }

    @Test
    fun `ID목록에 해당하는 유저가 없다면 빈 List를 반환할 수 있다`() {
        val idList = listOf(998L, 999L)
        val emptyList = emptyList<UserEntity>()

        `when`(userRepository.findAllByIdIn(idList = idList)).thenReturn(emptyList)

        val actual = sut.findAllUserBy(idList = idList)

        assertEquals(actual?.size, 0)
        verify(userRepository, times(1)).findAllByIdIn(idList = idList)
    }
}
