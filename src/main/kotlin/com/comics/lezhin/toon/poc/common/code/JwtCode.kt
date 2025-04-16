package com.comics.lezhin.toon.poc.common.code

import org.springframework.http.HttpStatus

enum class JwtCode(
    private val code: String,
    private val message: String,
    private val httpStatus: HttpStatus,
) : Code {
    FAIL_EXTRACT_SUBJECT("US401", "토큰에서 사용자 정보를 추출할 수 없습니다.", HttpStatus.UNAUTHORIZED),
    ;

    override fun getCode(): String = code

    override fun getMessage(): String = message

    override fun getHttpStatus(): String = httpStatus.name
}
