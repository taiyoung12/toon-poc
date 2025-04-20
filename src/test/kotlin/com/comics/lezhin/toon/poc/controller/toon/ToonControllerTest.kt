package com.comics.lezhin.toon.poc.controller.toon

import com.comics.lezhin.toon.poc.application.toon.ToonApplication
import com.comics.lezhin.toon.poc.application.toon.ToonViewApplication
import com.comics.lezhin.toon.poc.common.code.ToonViewCode
import com.comics.lezhin.toon.poc.controller.BaseApiTest
import com.comics.lezhin.toon.poc.controller.response.ReadToonViewHistoryResponse
import com.comics.lezhin.toon.poc.controller.response.ToonViewHistoryDto
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(ToonController::class)
class ToonControllerTest : BaseApiTest() {
    @MockitoBean
    private lateinit var toonViewApplication: ToonViewApplication

    @MockitoBean
    private lateinit var toonApplication: ToonApplication

    @Test
    fun `작품 조회 이력을 조회할 수 있다`() {
        val toonId = 1L
        val now = LocalDateTime.now()
        val response =
            ReadToonViewHistoryResponse(
                viewHistories =
                    listOf(
                        ToonViewHistoryDto(
                            name = "한우진",
                            email = "lezhin1@test.com",
                            viewedAt = listOf(now, now.minusDays(1)),
                        ),
                        ToonViewHistoryDto(
                            name = "도가영",
                            email = "lezhin2@test.com",
                            viewedAt = listOf(now.minusDays(2)),
                        ),
                    ),
            )

        `when`(toonViewApplication.readToonViewHistory(id = toonId)).thenReturn(response)

        mockMvc
            .perform(
                get("/api/v1/toon/{toonId}/viewed", toonId)
                    .accept(MediaType.APPLICATION_JSON),
            ).andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "toon/view-history/success",
                    pathParameters(
                        parameterWithName("toonId").description("웹툰 ID"),
                    ),
                    responseFields(
                        fieldWithPath("code").description(ToonViewCode.SUCCESS.getCode()),
                        fieldWithPath("message").description(ToonViewCode.SUCCESS.getMessage()),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.viewHistories").description("웹툰 조회 기록 목록"),
                        fieldWithPath("data.viewHistories[].name").description("사용자 이름"),
                        fieldWithPath("data.viewHistories[].email").description("사용자 이메일"),
                        fieldWithPath("data.viewHistories[].viewedAt").description("조회 일시 목록"),
                        fieldWithPath("data.viewHistories[].viewedAt[]").description("조회 일시"),
                    ),
                ),
            )
    }

    @Test
    fun `작품 조회 이력 조회 시 리스트가 비어있을 수 있다`() {
        val toonId = 999L

        `when`(toonViewApplication.readToonViewHistory(anyLong())).thenReturn(null)

        mockMvc
            .perform(
                get("/api/v1/toon/{toonId}/viewed", toonId)
                    .accept(MediaType.APPLICATION_JSON),
            ).andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "toon/view-history/empty",
                    pathParameters(
                        parameterWithName("toonId").description("웹툰 ID"),
                    ),
                    responseFields(
                        fieldWithPath("code").description(ToonViewCode.SUCCESS.getCode()),
                        fieldWithPath("message").description(ToonViewCode.SUCCESS.getMessage()),
                        fieldWithPath("data").description("응답 데이터 (null)"),
                    ),
                ),
            )
    }
}
