package com.comics.lezhin.toon.poc.controller

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.application.AuthApplication
import com.comics.lezhin.toon.poc.common.code.AuthCode
import com.comics.lezhin.toon.poc.controller.auth.AuthController
import com.comics.lezhin.toon.poc.controller.request.SigninRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AuthController::class)
class AuthControllerTest : BaseApiTest() {
    @MockitoBean
    private lateinit var authApplication: AuthApplication

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `로그인을 성공할 수 있다`() {
        val email = "lezhin1@test.com"
        val password = "lezhin123!"
        val signinRequest = SigninRequest(email = email, password = password)
        val accessToken = "accessToken"

        `when`(authApplication.signin(anyString(), anyString())).thenReturn(accessToken)

        mockMvc
            .perform(
                post("/api/v1/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signinRequest)),
            ).andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "auth/signin/success",
                    requestFields(
                        fieldWithPath("email").description("사용자 이메일"),
                        fieldWithPath("password").description("사용자 비밀번호"),
                    ),
                    responseFields(
                        fieldWithPath("code").description("AT200"),
                        fieldWithPath("message").description("성공"),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.accessToken").description("인증 토큰"),
                    ),
                ),
            )
    }

    @Test
    fun `email이 일치하는 유저가 없다면 실패할 수 있다`() {
        val email = "noUser@test.com"
        val wrongPassword = "-"
        val signinRequest = SigninRequest(email = email, password = wrongPassword)

        `when`(authApplication.signin(email, wrongPassword))
            .thenThrow(BaseException(AuthCode.NOT_FOUND_USER_BY_EMAIL))

        mockMvc
            .perform(
                post("/api/v1/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signinRequest)),
            ).andExpect(status().isNotFound())
            .andDo(print())
            .andDo(
                document(
                    "auth/signin/not-found-user-error",
                    requestFields(
                        fieldWithPath("email").description("사용자 이메일"),
                        fieldWithPath("password").description("사용자 비밀번호"),
                    ),
                    responseFields(
                        fieldWithPath("code").description(AuthCode.NOT_FOUND_USER_BY_EMAIL.getCode()),
                        fieldWithPath("message").description(AuthCode.NOT_FOUND_USER_BY_EMAIL.getMessage()),
                        subsectionWithPath("data").description("응답 데이터 (null)"),
                    ),
                ),
            )
    }

    @Test
    fun `password가 없다면 실패할 수 있다`() {
        val email = "lezhin1@test.com"
        val wrongPassword = "wrong_password"
        val signinRequest = SigninRequest(email = email, password = wrongPassword)

        `when`(authApplication.signin(email, wrongPassword))
            .thenThrow(BaseException(AuthCode.NOT_MATCHED_USER_PASSWORD))

        mockMvc
            .perform(
                post("/api/v1/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signinRequest)),
            ).andExpect(status().isUnauthorized())
            .andDo(print())
            .andDo(
                document(
                    "auth/signin/password-error",
                    requestFields(
                        fieldWithPath("email").description("사용자 이메일"),
                        fieldWithPath("password").description("사용자 비밀번호"),
                    ),
                    responseFields(
                        fieldWithPath("code").description(AuthCode.NOT_MATCHED_USER_PASSWORD.getCode()),
                        fieldWithPath("message").description(AuthCode.NOT_MATCHED_USER_PASSWORD.getMessage()),
                        subsectionWithPath("data").description("응답 데이터 (null)"),
                    ),
                ),
            )
    }
}
