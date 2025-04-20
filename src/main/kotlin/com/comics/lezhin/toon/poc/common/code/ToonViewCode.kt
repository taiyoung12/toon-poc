package com.comics.lezhin.toon.poc.common.code

import org.springframework.http.HttpStatus

enum class ToonViewCode(
    private val code: String,
    private val message: String,
    private val httpStatus: HttpStatus,
) : Code {
    SUCCESS("TV200", "성공", HttpStatus.OK),
    ;

    override fun getCode(): String = code

    override fun getMessage(): String = message

    override fun getHttpStatus(): String = httpStatus.name
}
