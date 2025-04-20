package com.comics.lezhin.toon.poc.common.code

import org.springframework.http.HttpStatus

enum class CoinCode(
    private val code: String,
    private val message: String,
    private val httpStatus: HttpStatus,
) : Code {
    NO_CHARGE_HISTORY("CN404", "충전 이력이 없습니다.", HttpStatus.NOT_FOUND),
    INSUFFICIENT_BALANCE("CN400", "잔액이 부족합니다.", HttpStatus.BAD_REQUEST),
    ;

    override fun getCode(): String = code

    override fun getMessage(): String = message

    override fun getHttpStatus(): String = httpStatus.name
}
