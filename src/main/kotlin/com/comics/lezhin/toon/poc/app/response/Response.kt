package com.comics.lezhin.toon.poc.app.response

import com.comics.lezhin.toon.poc.common.code.Code
import com.comics.lezhin.toon.poc.common.code.CommonCode

data class Response<T>(
    val code: String,
    val message: String,
    val data: T?,
) {
    companion object {
        fun <T> success(data: T): Response<T> =
            Response(
                CommonCode.SUCCESS.getCode(),
                CommonCode.SUCCESS.getMessage(),
                data,
            )

        fun <T> success(
            code: Code,
            data: T,
        ): Response<T> = Response(code.getCode(), code.getMessage(), data)

        fun success(responseCode: Code): Response<Void?> = Response(responseCode.getCode(), responseCode.getMessage(), null)

        fun <T> error(
            code: Code,
            data: T,
        ): Response<T> = Response(code.getCode(), code.getMessage(), data)

        fun error(code: Code): Response<Void?> = Response(code.getCode(), code.getMessage(), null)
    }
}
