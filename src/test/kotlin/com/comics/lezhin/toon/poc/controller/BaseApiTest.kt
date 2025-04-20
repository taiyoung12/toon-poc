package com.comics.lezhin.toon.poc.controller

import com.comics.lezhin.toon.poc.app.config.AppConfig
import com.comics.lezhin.toon.poc.app.interceptor.JwtInterceptor
import com.comics.lezhin.toon.poc.app.token.JwtValidator
import com.comics.lezhin.toon.poc.controller.config.DummyUserIdAnnotationTestConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc

@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
@Import(
    value = [
        DummyUserIdAnnotationTestConfig::class,
    ],
)
abstract class BaseApiTest {
    @Autowired
    protected lateinit var mockMvc: MockMvc

    @MockitoBean
    protected lateinit var jwtValidator: JwtValidator

    @MockitoBean
    protected lateinit var jwtInterceptor: JwtInterceptor

    @MockitoBean
    protected lateinit var appConfig: AppConfig
}
