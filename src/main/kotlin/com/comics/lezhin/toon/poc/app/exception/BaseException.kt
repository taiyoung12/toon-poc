package com.comics.lezhin.toon.poc.app.exception

import com.comics.lezhin.toon.poc.common.code.Code

open class BaseException(
    val code: Code,
    message: String = code.getMessage(),
) : RuntimeException(message)
