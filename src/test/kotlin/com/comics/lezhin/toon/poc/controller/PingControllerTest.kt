package com.comics.lezhin.toon.poc.controller

import com.comics.lezhin.toon.poc.controller.health.PingController
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PingController::class)
class PingControllerTest : BaseApiTest() {
    @Test
    fun `ping 응답 문서 생성`() {
        mockMvc
            .perform(
                get("/api/v1/ping")
                    .accept(MediaType.APPLICATION_JSON),
            ).andExpect(status().isOk)
            .andDo(
                document(
                    "ping",
                    responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data").description("pong 문자열 데이터"),
                    ),
                ),
            )
    }
}
