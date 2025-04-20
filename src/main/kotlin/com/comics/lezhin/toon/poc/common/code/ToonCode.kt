package com.comics.lezhin.toon.poc.common.code

import org.springframework.http.HttpStatus

enum class ToonCode(
    private val code: String,
    private val message: String,
    private val httpStatus: HttpStatus,
) : Code {
    NOT_FOUND_TOON_BY_ID("TN001", "toonId에 해당하는 toon이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    FILTER_MINOR("TN002", "미성년자는 성인 컨텐츠에 접근할 수 없습니다.", HttpStatus.UNAUTHORIZED),
    TOON_PURCHASE_SUCCESS("TP200", "웹툰 구매에 성공하였습니다.", HttpStatus.OK),
    SUCCESS_READ_POPULAR_TOON("TR200", "인기 작품 조회에 성공하였습니다.", HttpStatus.OK),
    SUCCESS_READ_PURCHASE_TOON("TPR200", "인기 구매 작품 조회에 성공하였습니다.", HttpStatus.OK),
    SUCCESS_DELETE_TOON_INFO("TND200", "작품 삭제에 성공하였습니다.", HttpStatus.OK),
    ;

    override fun getCode(): String = code

    override fun getMessage(): String = message

    override fun getHttpStatus(): String = httpStatus.name
}
