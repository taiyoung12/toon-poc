package com.comics.lezhin.toon.poc.app.config

import com.comics.lezhin.toon.poc.app.config.path.ApiPath
import com.comics.lezhin.toon.poc.app.interceptor.JwtInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AppConfig(
    private val jwtInterceptor: JwtInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(jwtInterceptor)
            .addPathPatterns(ApiPath.Toon.TOON_ALL)
            .excludePathPatterns(ApiPath.Ping.PING, ApiPath.Auth.AUTH_ALL)
    }
}
