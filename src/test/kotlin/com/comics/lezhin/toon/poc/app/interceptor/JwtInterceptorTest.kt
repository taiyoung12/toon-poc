package com.comics.lezhin.toon.poc.app.interceptor

import com.comics.lezhin.toon.poc.app.token.JwtValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

@ExtendWith(MockitoExtension::class)
class JwtInterceptorTest {
    @Mock
    lateinit var jwtValidator: JwtValidator

    lateinit var sut: JwtInterceptor

    @BeforeEach
    fun setUp() {
        sut = JwtInterceptor(jwtValidator)
    }

    @Test
    fun `유효한 토큰일 경우 Request가 통과할 수 있다`() {
        val token = "valid.jwt.token"
        val userId = "hanwoojin"
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        request.addHeader("Authorization", "Bearer $token")

        `when`(jwtValidator.validate(token)).thenReturn(true)
        `when`(jwtValidator.extractSubject(token)).thenReturn(userId)

        val actual = sut.preHandle(request, response, Any())

        assertTrue(actual)
        assertEquals(userId, request.getAttribute("userId"))
        assertEquals(200, response.status)
    }

    @Test
    fun `토큰이 없을 경우 401을 반환할 수 있다`() {
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        val actual = sut.preHandle(request, response, Any())

        assertFalse(actual)
        assertEquals(401, response.status)
        assertEquals("Unauthorized", response.contentAsString)
    }

    @Test
    fun `유효하지 않은 토큰일 경우 401을 반환할 수 있다`() {
        val token = "invalid.jwt.token"
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        request.addHeader("Authorization", "Bearer $token")
        `when`(jwtValidator.validate(token)).thenReturn(false)

        val actual = sut.preHandle(request, response, Any())

        assertFalse(actual)
        assertEquals(401, response.status)
        assertEquals("Unauthorized", response.contentAsString)
    }
}
