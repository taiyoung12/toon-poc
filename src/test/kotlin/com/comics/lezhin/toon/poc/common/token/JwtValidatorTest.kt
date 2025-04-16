package com.comics.lezhin.toon.poc.common.token

import com.comics.lezhin.toon.poc.common.code.JwtCode
import com.comics.lezhin.toon.poc.common.exception.BaseException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class JwtValidatorTest {
    private val secretKey = "lezhin-test-secret-key-lezhin-test-secret-key"
    private val accessTokenExpiration = 60 * 10 * 1000L

    @Mock
    private lateinit var jwtProperties: JwtProperties
    private lateinit var jwtGenerator: JwtGenerator
    private lateinit var sut: JwtValidator

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        `when`(jwtProperties.secretKey).thenReturn(secretKey)
        `when`(jwtProperties.accessTokenExpiration).thenReturn(accessTokenExpiration)

        jwtGenerator = JwtGenerator(jwtProperties)
        sut = JwtValidator(jwtProperties)
    }

    @Test
    fun `토큰_검증을_할_수_있다`() {
        val userId = "user123"
        val token = jwtGenerator.generateAccessToken(userId)

        val actual = sut.validate(token)

        assertThat(actual).isTrue()
    }

    @Test
    fun `토큰이_만료되면_검증을_실패할_수_있다`() {
        val userId = "user123"
        `when`(jwtProperties.accessTokenExpiration).thenReturn(-1000L)

        val generator = JwtGenerator(jwtProperties)
        val token = generator.generateAccessToken(userId)

        val actual = sut.validate(token)

        assertThat(actual).isFalse()
    }

    @Test
    fun `잘못된_형식의_토큰은_검증에_실패할_수_있다`() {
        val invalidToken = "invalid.token.format"

        val actual = sut.validate(invalidToken)

        assertThat(actual).isFalse()
    }

    @Test
    fun `토큰에서_subject를_추출할_수_있다`() {
        val userId = "user123"
        val token = jwtGenerator.generateAccessToken(userId)

        val actual = sut.extractSubject(token)

        assertThat(actual).isEqualTo(userId)
    }

    @Test
    fun `잘못된_형식의_토큰에서는_subject_추출에_실패할_수_있다`() {
        val invalidToken = "invalid.token.format"

        val exception =
            assertThrows<BaseException> {
                sut.extractSubject(invalidToken)
            }

        assertThat(exception.code).isEqualTo(JwtCode.FAIL_EXTRACT_SUBJECT)
    }
}
