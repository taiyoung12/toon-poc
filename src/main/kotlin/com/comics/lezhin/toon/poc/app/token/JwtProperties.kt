package com.comics.lezhin.toon.poc.app.token

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String,
    val accessTokenExpiration: Long,
)
