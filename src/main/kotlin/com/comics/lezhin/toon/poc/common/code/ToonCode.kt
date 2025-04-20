package com.comics.lezhin.toon.poc.common.code

import org.springframework.http.HttpStatus

enum class ToonCode(
    private val code: String,
    private val message: String,
    private val httpStatus: HttpStatus,
) : Code {
    NOT_FOUND_TOON_BY_ID("TN404", "toonId에 해당하는 toon이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;

    override fun getCode(): String = code

    override fun getMessage(): String = message

    override fun getHttpStatus(): String = httpStatus.name
}
