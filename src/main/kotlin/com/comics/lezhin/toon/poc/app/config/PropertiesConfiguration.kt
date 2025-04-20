package com.comics.lezhin.toon.poc.app.config

import com.comics.lezhin.toon.poc.app.token.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
class PropertiesConfiguration {
    @Configuration
    @EnableConfigurationProperties(JwtProperties::class)
    class JwtPropertiesConfiguration
}
