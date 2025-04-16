package com.comics.lezhin.toon.poc.common.config

import com.comics.lezhin.toon.poc.common.token.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
class PropertiesConfiguration {
    @Configuration
    @EnableConfigurationProperties(JwtProperties::class)
    class JwtPropertiesConfiguration
}
