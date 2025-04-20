package com.comics.lezhin.toon.poc.app.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class JwtGeneratorTest {
    private val secretKey = "lezhin-test-secret-key-lezhin-test-secret-key"
    private val accessTokenExpiration = 60 * 10 * 1000L

    @Mock
    private lateinit var jwtProperties: JwtProperties

    private lateinit var sut: JwtGenerator

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(jwtProperties.secretKey).thenReturn(secretKey)
        `when`(jwtProperties.accessTokenExpiration).thenReturn(accessTokenExpiration)

        sut = JwtGenerator(jwtProperties)
    }

    @Test
    fun `accessToken을 생성할 수 있다`() {
        val subject = "userId"
        val actual = sut.generateAccessToken(subject)

        assertThat(actual).isNotEmpty()
    }

    @Test
    fun `accessToken에 subject userId 가 포함될 수 있다`() {
        val subject = "user123"
        val token = sut.generateAccessToken(subject)

        val claims: Claims =
            Jwts
                .parserBuilder()
                .setSigningKey(secretKey.toByteArray())
                .build()
                .parseClaimsJws(token)
                .body

        assertThat(claims.subject).isEqualTo(subject)
    }

    @Test
    fun `accessToken의 만료 시간을 설정할 수 있다`() {
        val subject = "user123"
        val token = sut.generateAccessToken(subject)

        val claims: Claims =
            Jwts
                .parserBuilder()
                .setSigningKey(secretKey.toByteArray())
                .build()
                .parseClaimsJws(token)
                .body

        val expectedExpiration = System.currentTimeMillis() + accessTokenExpiration
        val actualExpiration = claims.expiration.time

        assertThat(kotlin.math.abs(expectedExpiration - actualExpiration)).isLessThan(1000)
    }
}
