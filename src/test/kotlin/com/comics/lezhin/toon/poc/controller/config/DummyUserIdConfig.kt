package com.comics.lezhin.toon.poc.controller.config

import com.comics.lezhin.toon.poc.app.annotation.UserId
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@TestConfiguration
class DummyUserIdAnnotationTestConfig : WebMvcConfigurer {
    @Bean(name = ["dummyUserIdArgumentResolver"])
    fun userIdArgumentResolver() =
        object : HandlerMethodArgumentResolver {
            override fun supportsParameter(parameter: MethodParameter) = parameter.hasParameterAnnotation(UserId::class.java)

            override fun resolveArgument(
                parameter: MethodParameter,
                mavContainer: ModelAndViewContainer?,
                webRequest: NativeWebRequest,
                binderFactory: WebDataBinderFactory?,
            ): Long = 1L
        }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userIdArgumentResolver())
    }
}
