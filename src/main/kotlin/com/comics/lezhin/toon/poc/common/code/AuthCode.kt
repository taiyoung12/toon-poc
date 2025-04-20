package com.comics.lezhin.toon.poc.common.code

import org.springframework.http.HttpStatus

enum class AuthCode(
    private val code: String,
    private val message: String,
    private val httpStatus: HttpStatus,
) : Code {
    SUCCESS("AT200", "성공", HttpStatus.OK),
    NOT_FOUND_USER_BY_EMAIL("AT001", "email을 가진 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_MATCHED_USER_PASSWORD("AT002", "password가 일치하지 않습니다", HttpStatus.UNAUTHORIZED),
    NOT_FOUND_USER_BY_ID("AT003", "id를 가진 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;

    override fun getCode(): String = code

    override fun getMessage(): String = message

    override fun getHttpStatus(): String = httpStatus.name
}
