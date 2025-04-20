package com.comics.lezhin.toon.poc.app.exception

import com.comics.lezhin.toon.poc.common.code.CommonCode

class ValidationException(
    val errors: Map<String, String>,
) : BaseException(CommonCode.VALIDATION_ERROR)
