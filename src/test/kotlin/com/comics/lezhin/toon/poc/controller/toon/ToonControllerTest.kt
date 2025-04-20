package com.comics.lezhin.toon.poc.controller.toon

import com.comics.lezhin.toon.poc.app.exception.BaseException
import com.comics.lezhin.toon.poc.application.toon.ToonApplication
import com.comics.lezhin.toon.poc.application.toon.ToonPurchaseApplication
import com.comics.lezhin.toon.poc.application.toon.ToonViewApplication
import com.comics.lezhin.toon.poc.common.code.CoinCode
import com.comics.lezhin.toon.poc.common.code.ToonCode
import com.comics.lezhin.toon.poc.common.code.ToonViewCode
import com.comics.lezhin.toon.poc.controller.BaseApiTest
import com.comics.lezhin.toon.poc.controller.response.PurchaseToonRankDto
import com.comics.lezhin.toon.poc.controller.response.ReadToonPurchaseResponse
import com.comics.lezhin.toon.poc.controller.response.ReadToonRankResponse
import com.comics.lezhin.toon.poc.controller.response.ReadToonViewHistoryResponse
import com.comics.lezhin.toon.poc.controller.response.ToonRankDto
import com.comics.lezhin.toon.poc.controller.response.ToonViewHistoryDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(ToonController::class)
class ToonControllerTest : BaseApiTest() {
    @MockitoBean
    private lateinit var toonViewApplication: ToonViewApplication

    @MockitoBean
    private lateinit var toonApplication: ToonApplication

    @MockitoBean
    private lateinit var toonPurchaseApplication: ToonPurchaseApplication

    @BeforeEach
    fun setUp() {
        `when`(jwtValidator.validate(anyString())).thenReturn(true)
        `when`(jwtValidator.extractSubject(anyString())).thenReturn("1")
    }

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

    @Test
    fun `작품을 구매할 수 있다`() {
        val toonId = 1L
        val userId = 1L
        val amount = 7

        `when`(toonApplication.purchase(userId, toonId)).thenReturn(amount)

        mockMvc
            .perform(
                post("/api/v1/toon/{toonId}/purchase", toonId)
                    .header("Authorization", "Bearer dummy-token")
                    .accept(MediaType.APPLICATION_JSON),
            ).andExpect(status().isOk)
            .andDo(print())
            .andDo(
                document(
                    "toon/purchase/success",
                    pathParameters(
                        parameterWithName("toonId").description("웹툰 ID"),
                    ),
                    responseFields(
                        fieldWithPath("code").description(ToonCode.TOON_PURCHASE_SUCCESS.getCode()),
                        fieldWithPath("message").description(ToonCode.TOON_PURCHASE_SUCCESS.getMessage()),
                        fieldWithPath("data.balance").description("구매 후 남은 코인 잔액"),
                    ),
                ),
            )
    }

    @Test
    fun `미성년자는 성인 작품을 구매할 수 없다`() {
        val toonId = 1L
        val userId = 1L

        `when`(toonApplication.purchase(userId, toonId)).thenThrow(BaseException(ToonCode.FILTER_MINOR))

        mockMvc
            .perform(
                post("/api/v1/toon/{toonId}/purchase", toonId)
                    .header("Authorization", "Bearer dummy-token")
                    .accept(MediaType.APPLICATION_JSON),
            ).andExpect(status().isUnauthorized)
            .andDo(print())
            .andDo(
                document(
                    "toon/purchase/filter/adult",
                    pathParameters(
                        parameterWithName("toonId").description("웹툰 ID"),
                    ),
                    responseFields(
                        fieldWithPath("code").description(ToonCode.FILTER_MINOR.getCode()),
                        fieldWithPath("message").description(ToonCode.FILTER_MINOR.getMessage()),
                        fieldWithPath("data").description(ToonCode.FILTER_MINOR.getMessage()),
                    ),
                ),
            )
    }

    @Test
    fun `코인이 부족하면 작품을 구매할 수 없다`() {
        val toonId = 1L
        val userId = 1L

        `when`(toonApplication.purchase(userId, toonId)).thenThrow(BaseException((CoinCode.INSUFFICIENT_BALANCE)))

        mockMvc
            .perform(
                post("/api/v1/toon/{toonId}/purchase", toonId)
                    .header("Authorization", "Bearer dummy-token")
                    .accept(MediaType.APPLICATION_JSON),
            ).andExpect(status().isBadRequest)
            .andDo(print())
            .andDo(
                document(
                    "toon/purchase/no/coin",
                    pathParameters(
                        parameterWithName("toonId").description("웹툰 ID"),
                    ),
                    responseFields(
                        fieldWithPath("code").description(CoinCode.INSUFFICIENT_BALANCE.getCode()),
                        fieldWithPath("message").description(CoinCode.INSUFFICIENT_BALANCE.getMessage()),
                        fieldWithPath("data").description(CoinCode.INSUFFICIENT_BALANCE.getMessage()),
                    ),
                ),
            )
    }

    @Test
    fun `인기 웹툰 Top 10을 조회할 수 있다`() {
        val userId = 1L
        val response =
            ReadToonRankResponse(
                rankings =
                    listOf(
                        ToonRankDto(
                            title = "인기 웹툰 1",
                            rank = 1,
                        ),
                        ToonRankDto(
                            title = "인기 웹툰 2",
                            rank = 2,
                        ),
                        ToonRankDto(
                            title = "인기 웹툰 3",
                            rank = 3,
                        ),
                    ),
            )

        `when`(toonViewApplication.readTop10(userId = userId)).thenReturn(response)

        mockMvc
            .perform(
                get("/api/v1/toon/popular/rank")
                    .header("Authorization", "Bearer dummy-token")
                    .accept(MediaType.APPLICATION_JSON),
            ).andExpect(status().isOk)
            .andDo(print())
            .andDo(
                document(
                    "toon/rank/success",
                    responseFields(
                        fieldWithPath("code").description(ToonCode.SUCCESS_READ_POPULAR_TOON.getCode()),
                        fieldWithPath("message").description(ToonCode.SUCCESS_READ_POPULAR_TOON.getMessage()),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.rankings").description("인기 웹툰 랭킹 목록"),
                        fieldWithPath("data.rankings[].title").description("웹툰 제목"),
                        fieldWithPath("data.rankings[].rank").description("웹툰 순위"),
                    ),
                ),
            )
    }

    @Test
    fun `구매 인기 웹툰 Top 10을 조회할 수 있다`() {
        val userId = 1L
        val response =
            ReadToonPurchaseResponse(
                rankings =
                    listOf(
                        PurchaseToonRankDto(
                            title = "구매 인기 웹툰 1",
                            rank = 1,
                        ),
                        PurchaseToonRankDto(
                            title = "구매 인기 웹툰 2",
                            rank = 2,
                        ),
                        PurchaseToonRankDto(
                            title = "구매 인기 웹툰 3",
                            rank = 3,
                        ),
                    ),
            )

        `when`(toonPurchaseApplication.readTop10()).thenReturn(response)

        mockMvc
            .perform(
                get("/api/v1/toon/purchase/rank")
                    .header("Authorization", "Bearer dummy-token")
                    .accept(MediaType.APPLICATION_JSON),
            ).andExpect(status().isOk)
            .andDo(print())
            .andDo(
                document(
                    "toon/purchase/rank/success",
                    responseFields(
                        fieldWithPath("code").description(ToonCode.SUCCESS_READ_PURCHASE_TOON.getCode()),
                        fieldWithPath("message").description(ToonCode.SUCCESS_READ_PURCHASE_TOON.getMessage()),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.rankings").description("인기 웹툰 랭킹 목록"),
                        fieldWithPath("data.rankings[].title").description("웹툰 제목"),
                        fieldWithPath("data.rankings[].rank").description("웹툰 순위"),
                    ),
                ),
            )
    }
}
