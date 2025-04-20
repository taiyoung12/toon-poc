package com.comics.lezhin.toon.poc.app.token

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.common.code.JwtCode
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtValidator(
    private val jwtProperties: JwtProperties,
) {
    private val secretKey: Key = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())

    fun validate(token: String): Boolean =
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        }

    fun extractSubject(token: String): String =
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
        } catch (e: JwtException) {
            throw BaseException(JwtCode.FAIL_EXTRACT_SUBJECT)
        }
}
