package com.comics.lezhin.toon.poc.common.token

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties,
) {
    private val secretKey: Key = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())

    fun generateAccessToken(subject: String): String {
        val now = System.currentTimeMillis()
        return Jwts
            .builder()
            .setSubject(subject)
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + jwtProperties.accessTokenExpiration))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }
}
